import { useEffect, useState } from 'react';
import type { Account } from '../types/Account';

export function AccountList() {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/accounts')
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Erreur HTTP ${response.status}`);
                }
                return response.json();
            })
            .then((data: Account[]) => {
                setAccounts(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Chargement des comptes...</p>;
    if (error) return <p>Erreur : {error}</p>;

    return (
        <ul>
            {accounts.map((account) => (
                <li key={account.id}>
                    {account.titulaire} — {(account.soldeCentimes / 100).toFixed(2)} €
                </li>
            ))}
        </ul>
    );
}