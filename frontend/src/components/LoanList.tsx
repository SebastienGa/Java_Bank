import { useEffect, useState } from 'react';
import type { Loan } from '../types/Account';

export function LoanList() {
    const [loans, setLoans] = useState<Loan[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch(`${import.meta.env.VITE_API_URL}/api/loans`)
            .then((response) => {
                if (!response.ok) throw new Error(`Erreur HTTP ${response.status}`);
                return response.json();
            })
            .then((data: Loan[]) => {
                setLoans(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="state-message">Chargement des prêts...</p>;
    if (error) return <p className="state-message">Erreur : {error}</p>;

    return (
        <>
            <p className="section-label">Vos prêts</p>
            <div className="loan-grid">
                {loans.map((loan) => (
                    <div className="loan-card" key={loan.id}>
                        <p className="loan-client">{loan.clientPrenom} {loan.clientNom}</p>
                        <p className="loan-type">Prêt en cours</p>
                        <div className="loan-amounts">
                            <span className="restant">{(loan.montantRestantCentimes / 100).toFixed(2)}€</span>
                            <span className="initial">sur {(loan.montantInitialCentimes / 100).toFixed(2)}€</span>
                        </div>
                        <div className="progress-track">
                            <div className="progress-fill" style={{ width: `${loan.progression}%` }} />
                        </div>
                        <div className="loan-meta">
                            <span>Mensualité : {(loan.mensualiteCentimes / 100).toFixed(2)}€</span>
                            <span>Taux : {loan.tauxInteretPourMille / 10}%</span>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}
