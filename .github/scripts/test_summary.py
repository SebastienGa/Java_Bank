#!/usr/bin/env python3
"""Agrège les rapports Surefire et JaCoCo du backend en un résumé JSON et Markdown.

Usage : test_summary.py --surefire-dir DIR --jacoco-xml FILE --output FILE

Le résumé Markdown est ajouté à $GITHUB_STEP_SUMMARY quand la variable existe.
Le script n'échoue jamais à cause de rapports absents (ex. erreur de compilation) :
c'est l'étape Maven qui porte le statut du job.
"""

import argparse
import json
import os
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

MESSAGE_MAX = 500


def run_metadata():
    server = os.environ.get("GITHUB_SERVER_URL", "https://github.com")
    repository = os.environ.get("GITHUB_REPOSITORY")
    run_id = os.environ.get("GITHUB_RUN_ID")
    run_url = f"{server}/{repository}/actions/runs/{run_id}" if repository and run_id else None
    return {
        "commit": os.environ.get("GITHUB_SHA"),
        # GITHUB_HEAD_REF n'est renseigné que pour les pull requests.
        "branch": os.environ.get("GITHUB_HEAD_REF") or os.environ.get("GITHUB_REF_NAME"),
        "run_url": run_url,
    }


def parse_surefire(surefire_dir):
    totals = {"tests": 0, "failures": 0, "errors": 0, "skipped": 0}
    failed = []
    reports = sorted(Path(surefire_dir).glob("TEST-*.xml")) if surefire_dir.is_dir() else []

    for report in reports:
        try:
            suite = ET.parse(report).getroot()
        except ET.ParseError as e:
            print(f"Rapport Surefire illisible ignoré : {report} ({e})", file=sys.stderr)
            continue
        for key in totals:
            totals[key] += int(suite.get(key, 0))
        for case in suite.iter("testcase"):
            for kind in ("failure", "error"):
                problem = case.find(kind)
                if problem is not None:
                    message = (problem.get("message") or problem.get("type") or "").strip()
                    failed.append({
                        "class": case.get("classname"),
                        "method": case.get("name"),
                        "kind": kind,
                        "message": message[:MESSAGE_MAX],
                    })

    totals["report_files"] = len(reports)
    return totals, failed


def parse_line_coverage(jacoco_xml):
    if not jacoco_xml.is_file():
        return None
    try:
        root = ET.parse(jacoco_xml).getroot()
    except ET.ParseError as e:
        print(f"jacoco.xml illisible : {e}", file=sys.stderr)
        return None
    # Les compteurs enfants directs de <report> sont les totaux globaux.
    for counter in root.findall("counter"):
        if counter.get("type") == "LINE":
            covered = int(counter.get("covered", 0))
            missed = int(counter.get("missed", 0))
            total = covered + missed
            return {
                "covered": covered,
                "missed": missed,
                "percent": round(covered * 100 / total, 2) if total else 0.0,
            }
    return None


def escape_cell(text):
    return (text or "").replace("|", "\\|").replace("\r", " ").replace("\n", " ")


def to_markdown(summary):
    tests = summary["tests"]
    coverage = summary["line_coverage"]
    ok = tests["report_files"] > 0 and tests["failures"] == 0 and tests["errors"] == 0
    status = "✅ Tests backend OK" if ok else "❌ Tests backend en échec"

    lines = [f"## {status}", ""]
    if tests["report_files"] == 0:
        lines += ["Aucun rapport Surefire trouvé (échec de compilation ?).", ""]
    lines += [
        "| Exécutés | Échecs | Erreurs | Ignorés | Couverture lignes |",
        "|---:|---:|---:|---:|---:|",
        "| {} | {} | {} | {} | {} |".format(
            tests["tests"], tests["failures"], tests["errors"], tests["skipped"],
            f"{coverage['percent']} % ({coverage['covered']}/{coverage['covered'] + coverage['missed']})"
            if coverage else "n/a",
        ),
        "",
    ]
    if summary["failed_tests"]:
        lines += ["### Tests en échec", "", "| Classe | Méthode | Type | Message |", "|---|---|---|---|"]
        for t in summary["failed_tests"]:
            lines.append("| {} | {} | {} | {} |".format(
                escape_cell(t["class"]), escape_cell(t["method"]), t["kind"], escape_cell(t["message"])))
        lines.append("")
    if summary["commit"]:
        lines.append(f"Commit `{summary['commit'][:12]}` sur `{summary['branch']}`.")
    return "\n".join(lines) + "\n"


def main():
    parser = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    parser.add_argument("--surefire-dir", type=Path, required=True)
    parser.add_argument("--jacoco-xml", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()

    totals, failed = parse_surefire(args.surefire_dir)
    summary = {
        **run_metadata(),
        "tests": totals,
        "failed_tests": failed,
        "line_coverage": parse_line_coverage(args.jacoco_xml),
    }

    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(summary, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")

    markdown = to_markdown(summary)
    step_summary = os.environ.get("GITHUB_STEP_SUMMARY")
    if step_summary:
        with open(step_summary, "a", encoding="utf-8") as f:
            f.write(markdown)
    else:
        # Console Windows (cp1252) : forcer l'UTF-8 pour les emojis du résumé.
        sys.stdout.reconfigure(encoding="utf-8")
        sys.stdout.write(markdown)


if __name__ == "__main__":
    main()
