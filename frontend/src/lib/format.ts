const euros = new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' });
const pourcentage = new Intl.NumberFormat('fr-FR', { style: 'percent', maximumFractionDigits: 1 });
const dateLongue = new Intl.DateTimeFormat('fr-FR', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });

export function formatCentimes(centimes: number): string {
    return euros.format(centimes / 100);
}

export function formatPourcentage(ratio: number): string {
    return pourcentage.format(ratio);
}

export function formatDateLongue(date: Date): string {
    return dateLongue.format(date);
}

export function masquerIdentifiant(id: string): string {
    return `•••• ${id.replaceAll('-', '').slice(-4).toUpperCase()}`;
}
