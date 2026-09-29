// ⚠️ Données de DÉMONSTRATION : le backend n'expose pas (encore) d'historique de
// transactions, de cartes ni de budget. Elles servent uniquement à montrer l'interface
// cible et sont toujours affichées avec la mention « démo » dans l'UI.

export type Category = 'Courses' | 'Transport' | 'Loisirs' | 'Logement' | 'Santé' | 'Revenus';

export interface DemoTransaction {
    id: string;
    label: string;
    category: Category;
    amountCentimes: number; // négatif = débit
    date: Date;
}

export const CATEGORY_COLORS: Record<Exclude<Category, 'Revenus'>, string> = {
    Logement: '#22d3ee',
    Courses: '#8b5cf6',
    Transport: '#34d399',
    Loisirs: '#f472b6',
    Santé: '#fbbf24',
};

function daysAgo(n: number): Date {
    const d = new Date();
    d.setHours(12, 0, 0, 0);
    d.setDate(d.getDate() - n);
    return d;
}

export function buildDemoTransactions(): DemoTransaction[] {
    return [
        { id: 't1', label: 'Carrefour Market', category: 'Courses', amountCentimes: -4632, date: daysAgo(0) },
        { id: 't2', label: 'Salaire — IBM France', category: 'Revenus', amountCentimes: 312000, date: daysAgo(1) },
        { id: 't3', label: 'Pass Ilévia', category: 'Transport', amountCentimes: -3900, date: daysAgo(2) },
        { id: 't4', label: 'Loyer', category: 'Logement', amountCentimes: -72000, date: daysAgo(3) },
        { id: 't5', label: 'Cinéma UGC Lille', category: 'Loisirs', amountCentimes: -1450, date: daysAgo(4) },
        { id: 't6', label: 'Pharmacie du Vieux-Lille', category: 'Santé', amountCentimes: -1280, date: daysAgo(6) },
        { id: 't7', label: 'Auchan', category: 'Courses', amountCentimes: -8875, date: daysAgo(8) },
        { id: 't8', label: 'Steam', category: 'Loisirs', amountCentimes: -2999, date: daysAgo(9) },
    ];
}

export function spendingByCategory(transactions: DemoTransaction[]) {
    const totals = new Map<Exclude<Category, 'Revenus'>, number>();
    for (const t of transactions) {
        if (t.category === 'Revenus' || t.amountCentimes >= 0) continue;
        totals.set(t.category, (totals.get(t.category) ?? 0) - t.amountCentimes);
    }
    return [...totals.entries()]
        .map(([category, centimes]) => ({ category, centimes }))
        .sort((a, b) => b.centimes - a.centimes);
}

/** Série de 30 points se terminant exactement sur `endCentimes` (courbe décorative, déterministe). */
export function buildBalanceHistory(endCentimes: number): number[] {
    const points = 30;
    const base = Math.max(endCentimes, 100000);
    return Array.from({ length: points }, (_, i) => {
        if (i === points - 1) return endCentimes;
        const wave = Math.sin(i * 0.55) * 0.05 + Math.sin(i * 0.21 + 1) * 0.04;
        const trend = (i / points) * 0.08 - 0.08;
        return Math.round(base * (1 + wave + trend));
    });
}

/** IBAN factice mais stable, dérivé de l'identifiant du compte. Non valide (démo). */
export function demoIban(accountId: string): string {
    const digits = accountId.replaceAll(/\D/g, '').padEnd(23, '7').slice(0, 23);
    const groups = digits.match(/.{1,4}/g) ?? [];
    return `FR76 ${groups.join(' ')}`.trim();
}
