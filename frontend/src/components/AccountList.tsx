import { useEffect, useState } from 'react';
import type { Account } from '../types/Account';

interface AccountListProps {
    onAccountsLoaded: (accounts: Account[]) => void;
}

export function AccountList({ onAccountsLoaded }: AccountListProps) {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/accounts')
            .then((response) => {
                if (!response.ok) throw new Error(`Erreur HTTP ${response.status}`);
                return response.json();
            })
            .then((data: Account[]) => {
                setAccounts(data);
                onAccountsLoaded(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, [onAccountsLoaded]);

    if (loading) return <p className="state-message">Chargement des comptes...</p>;
    if (error) return <p className="state-message">Erreur : {error}</p>;

    return (
        <>
            <p className="section-label">Vos comptes</p>
            <div className="account-grid">
                {accounts.map((account) => (
                    <div className="account-card" key={account.id}>
                        <p className="account-type">Compte courant</p>
                        <p className="titulaire">{account.clientPrenom} {account.clientNom}</p>
                        <span className="solde">{(account.soldeCentimes / 100).toFixed(2)}€</span>
                    </div>
                ))}
            </div>
        </>
    );
}