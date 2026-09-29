const euroFormatter = new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' });
const dateFormatter = new Intl.DateTimeFormat('fr-FR', { day: 'numeric', month: 'short' });
const longDateFormatter = new Intl.DateTimeFormat('fr-FR', {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
    year: 'numeric',
});

export function formatEuros(centimes: number): string {
    return euroFormatter.format(centimes / 100);
}

export function formatShortDate(date: Date): string {
    return dateFormatter.format(date);
}

export function formatLongDate(date: Date): string {
    return longDateFormatter.format(date);
}

export function initials(prenom: string, nom: string): string {
    return `${prenom.charAt(0)}${nom.charAt(0)}`.toUpperCase();
}
