export function Sparkline({ values }: Readonly<{ values: number[] }>) {
    const width = 600;
    const height = 120;
    const min = Math.min(...values);
    const max = Math.max(...values);
    const range = max - min || 1;
    const points = values.map((v, i) => {
        const x = (i / (values.length - 1)) * width;
        const y = height - 10 - ((v - min) / range) * (height - 30);
        return [x, y] as const;
    });
    const line = points.map(([x, y], i) => `${i === 0 ? 'M' : 'L'}${x.toFixed(1)},${y.toFixed(1)}`).join(' ');
    const area = `${line} L${width},${height} L0,${height} Z`;
    const [lastX, lastY] = points[points.length - 1];

    return (
        <svg className="sparkline" viewBox={`0 0 ${width} ${height}`} preserveAspectRatio="none" role="img"
             aria-label="Évolution du solde sur 30 jours (démonstration)">
            <defs>
                <linearGradient id="spark-fill" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="0%" stopColor="#22d3ee" stopOpacity="0.35" />
                    <stop offset="100%" stopColor="#22d3ee" stopOpacity="0" />
                </linearGradient>
                <linearGradient id="spark-line" x1="0" y1="0" x2="1" y2="0">
                    <stop offset="0%" stopColor="#8b5cf6" />
                    <stop offset="100%" stopColor="#22d3ee" />
                </linearGradient>
            </defs>
            <path d={area} fill="url(#spark-fill)" />
            <path d={line} fill="none" stroke="url(#spark-line)" strokeWidth="2.5" strokeLinejoin="round"
                  vectorEffect="non-scaling-stroke" />
            <circle cx={lastX} cy={lastY} r="5" fill="#22d3ee" />
        </svg>
    );
}
