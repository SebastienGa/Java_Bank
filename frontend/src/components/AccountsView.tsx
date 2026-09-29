import { useState } from 'react';
import toast from 'react-hot-toast';
import type { Account } from '../types/Account';
import { demoIban } from '../data/demo';
import { formatEuros, initials } from '../lib/format';
import { Icon } from './Icon';

function AccountRow({ account }: Readonly<{ account: Account }>) {
    const iban = demoIban(account.id);
    const [copied, setCopied] = useState(false);

    const copy = async () => {
        try {
            await navigator.clipboard.writeText(iban);
            setCopied(true);
            toast.success('IBAN copié (démo).');
            setTimeout(() => setCopied(false), 1800);
        } catch {
            toast.error('Copie impossible sur ce navigateur.');
        }
    };

    return (
        <article className="panel account-row">
            <span className="avatar">{initials(account.clientPrenom, account.clientNom)}</span>
            <div className="account-row-main">
                <p className="account-owner">{account.clientPrenom} {account.clientNom}</p>
                <p className="account-type">Compte courant</p>
                <p className="iban">
                    <span>{iban}</span>
                    <button className="icon-btn" onClick={copy} aria-label="Copier l'IBAN">
                        <Icon name={copied ? 'check' : 'copy'} size={16} />
                    </button>
                    <span className="badge">démo</span>
                </p>
            </div>
            <p className="account-balance">{formatEuros(account.soldeCentimes)}</p>
        </article>
    );
}

export function AccountsView({ accounts }: Readonly<{ accounts: Account[] }>) {
    return (
        <div className="stack">
            {accounts.map((a) => <AccountRow key={a.id} account={a} />)}
        </div>
    );
}
