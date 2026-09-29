import type { Account } from '../types/Account';
import { formatEuros } from '../lib/format';

export function AccountCard({ account }: Readonly<{ account: Account }>) {
    return (
        <article className="account-card">
            <p className="account-type">Compte courant</p>
            <p className="account-owner">{account.clientPrenom} {account.clientNom}</p>
            <p className="account-balance">{formatEuros(account.soldeCentimes)}</p>
        </article>
    );
}
