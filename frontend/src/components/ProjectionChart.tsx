import { useId, useState, type KeyboardEvent, type PointerEvent } from 'react';
import { formatCentimes } from '../lib/format';
import type { PointProjection } from '../lib/amortissement';

const LARGEUR = 560;
const HAUTEUR = 200;
const MARGE = { haut: 12, droite: 12, bas: 28, gauche: 56 };

const moisAnnee = new Intl.DateTimeFormat('fr-FR', { month: 'long', year: 'numeric' });
const compact = new Intl.NumberFormat('fr-FR', { notation: 'compact', maximumFractionDigits: 0 });

function graduationsY(max: number): number[] {
    const brut = max / 3;
    const puissance = 10 ** Math.floor(Math.log10(brut));
    const pas = [1, 2, 2.5, 5, 10].map((m) => m * puissance).find((p) => p >= brut) ?? brut;
    return [0, pas, pas * 2, pas * 3].filter((v) => v <= max * 1.05 || v === 0);
}

interface ProjectionChartProps {
    points: PointProjection[];
    titre: string;
}

export function ProjectionChart({ points, titre }: Readonly<ProjectionChartProps>) {
    const [survol, setSurvol] = useState<number | null>(null);
    const titreId = useId();

    const max = points[0].restantCentimes;
    const ticksY = graduationsY(max);
    const yMax = Math.max(max, ticksY.at(-1) ?? max);
    const largeurUtile = LARGEUR - MARGE.gauche - MARGE.droite;
    const hauteurUtile = HAUTEUR - MARGE.haut - MARGE.bas;
    const x = (i: number) => MARGE.gauche + (i / (points.length - 1)) * largeurUtile;
    const y = (v: number) => MARGE.haut + (1 - v / yMax) * hauteurUtile;

    const ligne = points.map((p, i) => `${i === 0 ? 'M' : 'L'}${x(i).toFixed(1)},${y(p.restantCentimes).toFixed(1)}`).join('');
    const aire = `${ligne}L${x(points.length - 1)},${y(0)}L${x(0)},${y(0)}Z`;

    const janviers = points.map((p, i) => ({ p, i })).filter(({ p }) => p.date.getMonth() === 0);
    const pasAnnees = Math.ceil(janviers.length / 6);
    const ticksX = janviers.filter((_, k) => k % pasAnnees === 0);

    const pointer = (e: PointerEvent<SVGSVGElement>) => {
        const rect = e.currentTarget.getBoundingClientRect();
        const xSvg = ((e.clientX - rect.left) / rect.width) * LARGEUR;
        const ratio = (xSvg - MARGE.gauche) / largeurUtile;
        setSurvol(Math.min(points.length - 1, Math.max(0, Math.round(ratio * (points.length - 1)))));
    };

    const clavier = (e: KeyboardEvent<SVGSVGElement>) => {
        const pas = e.shiftKey ? 12 : 1;
        if (e.key === 'ArrowRight') setSurvol((s) => Math.min(points.length - 1, (s ?? -1) + pas));
        else if (e.key === 'ArrowLeft') setSurvol((s) => Math.max(0, (s ?? points.length) - pas));
        else return;
        e.preventDefault();
    };

    const actif = survol === null ? null : points[survol];
    const resumeAnnuel = points.filter((p, i) => i === 0 || p.date.getMonth() === 0 || i === points.length - 1);

    return (
        <figure className="projection" aria-labelledby={titreId}>
            <figcaption id={titreId} className="projection-title">
                {titre}
            </figcaption>
            <div className="projection-plot">
                <svg
                    viewBox={`0 0 ${LARGEUR} ${HAUTEUR}`}
                    role="img"
                    aria-label={`${titre} : de ${formatCentimes(max)} aujourd’hui à 0 € en ${moisAnnee.format(points.at(-1)!.date)}. Flèches gauche et droite pour parcourir.`}
                    tabIndex={0}
                    onPointerMove={pointer}
                    onPointerLeave={() => setSurvol(null)}
                    onKeyDown={clavier}
                    onBlur={() => setSurvol(null)}
                >
                    {ticksY.map((v) => (
                        <g key={v} className="projection-grid">
                            <line x1={MARGE.gauche} x2={LARGEUR - MARGE.droite} y1={y(v)} y2={y(v)} />
                            <text x={MARGE.gauche - 8} y={y(v)} dy="0.32em" textAnchor="end">
                                {compact.format(v / 100)} €
                            </text>
                        </g>
                    ))}
                    {ticksX.map(({ p, i }) => (
                        <text key={i} className="projection-axis" x={x(i)} y={HAUTEUR - 8} textAnchor="middle">
                            {p.date.getFullYear()}
                        </text>
                    ))}
                    <path className="projection-area" d={aire} />
                    <path className="projection-line" d={ligne} />
                    <circle className="projection-dot" cx={x(0)} cy={y(max)} r={4} />
                    {actif && survol !== null && (
                        <g className="projection-cursor">
                            <line x1={x(survol)} x2={x(survol)} y1={MARGE.haut} y2={y(0)} />
                            <circle cx={x(survol)} cy={y(actif.restantCentimes)} r={4.5} />
                        </g>
                    )}
                </svg>
                {actif && survol !== null && (
                    <div
                        className="projection-tooltip"
                        style={{ left: `${(x(survol) / LARGEUR) * 100}%` }}
                        data-side={survol > points.length / 2 ? 'left' : 'right'}
                    >
                        <strong className="amount">{formatCentimes(actif.restantCentimes)}</strong>
                        <span>restant dû · {moisAnnee.format(actif.date)}</span>
                    </div>
                )}
            </div>
            <details className="projection-table">
                <summary>Voir les valeurs par année</summary>
                <table>
                    <thead>
                        <tr>
                            <th scope="col">Date</th>
                            <th scope="col">Capital restant dû</th>
                        </tr>
                    </thead>
                    <tbody>
                        {resumeAnnuel.map((p) => (
                            <tr key={p.date.toISOString()}>
                                <td>{moisAnnee.format(p.date)}</td>
                                <td className="amount">{formatCentimes(p.restantCentimes)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </details>
        </figure>
    );
}
