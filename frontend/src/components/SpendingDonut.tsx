import { CATEGORY_COLORS, spendingByCategory, type DemoTransaction } from '../data/demo';
import { formatEuros } from '../lib/format';

export function SpendingDonut({ transactions }: Readonly<{ transactions: DemoTransaction[] }>) {
    const spending = spendingByCategory(transactions);
    const total = spending.reduce((sum, s) => sum + s.centimes, 0);
    const radius = 54;
    const circumference = 2 * Math.PI * radius;

    const segments = spending.map((s, index) => {
        const before = spending.slice(0, index).reduce((sum, prev) => sum + prev.centimes, 0);
        return {
            ...s,
            length: (s.centimes / total) * circumference,
            offset: (before / total) * circumference,
        };
    });

    return (
        <section className="panel" aria-labelledby="spending-title">
            <header className="panel-header">
                <h2 id="spending-title">Dépenses par catégorie</h2>
                <span className="badge">démo</span>
            </header>
            <div className="donut-layout">
                <svg viewBox="0 0 140 140" className="donut" role="img" aria-label="Répartition des dépenses">
                    <circle cx="70" cy="70" r={radius} fill="none" stroke="rgba(255,255,255,0.06)" strokeWidth="14" />
                    {segments.map((s) => (
                        <circle
                            key={s.category}
                            cx="70"
                            cy="70"
                            r={radius}
                            fill="none"
                            stroke={CATEGORY_COLORS[s.category]}
                            strokeWidth="14"
                            strokeDasharray={`${Math.max(s.length - 3, 0)} ${circumference}`}
                            strokeDashoffset={-s.offset}
                            transform="rotate(-90 70 70)"
                        />
                    ))}
                    <text x="70" y="66" textAnchor="middle" className="donut-caption">Ce mois-ci</text>
                    <text x="70" y="86" textAnchor="middle" className="donut-total">{formatEuros(total)}</text>
                </svg>
                <ul className="legend">
                    {spending.map((s) => (
                        <li key={s.category}>
                            <span className="dot" style={{ background: CATEGORY_COLORS[s.category] }} />
                            <span className="legend-label">{s.category}</span>
                            <span className="legend-value">{formatEuros(s.centimes)}</span>
                        </li>
                    ))}
                </ul>
            </div>
        </section>
    );
}
