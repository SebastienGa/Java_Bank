import type { CSSProperties } from 'react';
import { Amount } from './Amount';
import { masquerIdentifiant } from '../lib/format';
import type { Account } from '../types/Account';

interface BankCardProps {
    account: Account;
    tint: string;
    index: number;
    onOpen?: () => void;
}

export function BankCard({ account, tint, index, onOpen }: Readonly<BankCardProps>) {
    const titulaire = `${account.clientPrenom} ${account.clientNom}`;
    const content = (
        <>
            <span className="bank-card-top">
                <span className="bank-card-type">Compte courant</span>
                <span className="chip" aria-hidden="true" />
            </span>
            <span className="bank-card-number">{masquerIdentifiant(account.id)}</span>
            <span className="bank-card-bottom">
                <span className="bank-card-holder">{titulaire}</span>
                <Amount centimes={account.soldeCentimes} className="bank-card-balance" />
            </span>
        </>
    );
    const style = { '--card-tint': tint, '--delay': `${index * 70}ms` } as CSSProperties;

    if (!onOpen) {
        return (
            <article className="bank-card" style={style}>
                {content}
            </article>
        );
    }
    return (
        <button
            type="button"
            className="bank-card bank-card-action"
            style={style}
            onClick={onOpen}
            aria-label={`Ouvrir la fiche de ${titulaire}`}
        >
            {content}
        </button>
    );
}
