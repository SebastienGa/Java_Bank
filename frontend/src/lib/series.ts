const TEINTES = ['var(--series-1)', 'var(--series-2)', 'var(--series-3)', 'var(--series-4)'];

export function teinteSerie(index: number): string {
    return TEINTES[index % TEINTES.length];
}
