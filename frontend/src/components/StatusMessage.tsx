interface StatusMessageProps {
    loading: boolean;
    waking: boolean;
    error: string | null;
    what: string;
}

/** Affiche l'état de chargement / d'erreur ; renvoie null quand il n'y a rien à signaler. */
export function StatusMessage({ loading, waking, error, what }: Readonly<StatusMessageProps>) {
    if (error) {
        return (
            <div className="status status-error" role="alert">
                <p>Impossible de charger {what}.</p>
                <p className="status-detail">{error}</p>
            </div>
        );
    }
    if (loading) {
        return (
            <div className="status" role="status">
                <span className="spinner" aria-hidden="true" />
                <p>Chargement de {what}…</p>
                {waking && (
                    <p className="status-detail">
                        Le serveur se réveille (hébergement gratuit), cela peut prendre jusqu'à une minute.
                    </p>
                )}
            </div>
        );
    }
    return null;
}
