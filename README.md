# Java_Bank

[![CI](https://github.com/SebastienGa/Java_Bank/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/SebastienGa/Java_Bank/actions/workflows/ci.yml)

Application bancaire Java 25 en architecture hexagonale (Ports &amp; Adapters), avec Spring Boot, PostgreSQL (Neon) et un front Angular. Projet portfolio destiné à démontrer une conception métier découplée de la technique, testable et évolutive — sert aussi de terrain d'usage à des agents IA de suivi de projet.

## CI

Le workflow `.github/workflows/ci.yml` compile, teste et mesure la couverture (JaCoCo) à chaque PR et push vers `main`.
Chaque run publie l'artefact `test-results` (Surefire, JaCoCo, `test-summary.json`), conservé 30 jours.
Sur `main`, le résumé est aussi poussé en clair sur la branche orpheline `ci-reports`.
Dernier rapport : https://raw.githubusercontent.com/SebastienGa/Java_Bank/ci-reports/latest.json
Historique : un fichier par run dans `runs/<AAAA-MM-JJ>-<sha7>.json` de cette branche.
