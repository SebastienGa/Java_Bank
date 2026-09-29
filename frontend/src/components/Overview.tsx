import { useMemo } from 'react';
import type { Account } from '../types/Account';
import { buildBalanceHistory, buildDemoTransactions } from '../data/demo';
import { formatEuros } from '../lib/format';
import type { View } from './Navigation';
import { AccountCard } from './AccountCard';
import { Icon } from './Icon';
import { Sparkline } from './Sparkline';
import { SpendingDonut } from './SpendingDonut';
import { TransactionList } from './TransactionList';

interface OverviewProps {
    accounts: Account[];
    onNavigate: (view: View) => void;
}

export function Overview({ accounts, onNavigate }: Readonly<OverviewProps>) {
    const total = accounts.reduce((sum, a) => sum + a.soldeCentimes, 0);
    const history = useMemo(() => buildBalanceHistory(total), [total]);
    const transactions = useMemo(() => buildDemoTransactions(), []);

    return (
        <div className="stack">
            <section className="hero">
                <div className="hero-text">
                    <p className="eyebrow">Solde total</p>
                    <p className="hero-amount">{formatEuros(total)}</p>
                    <p className="hero-sub">
                        Réparti sur {accounts.length} compte{accounts.length > 1 ? 's' : ''} · évolution 30 jours
                        <span className="badge">démo</span>
                    </p>
                    <div className="quick-actions">
                        <button className="btn btn-primary" onClick={() => onNavigate('transfer')}>
                            <Icon name="swap" size={18} /> Faire un virement
                        </button>
                        <button className="btn btn-ghost" onClick={() => onNavigate('cards')}>
                            <Icon name="card" size={18} /> Mes cartes
                        </button>
                        <button className="btn btn-ghost" onClick={() => onNavigate('loans')}>
                            <Icon name="loan" size={18} /> Mes prêts
                        </button>
                    </div>
                </div>
                <Sparkline values={history} />
            </section>

            <section aria-labelledby="accounts-title">
                <h2 id="accounts-title" className="section-title">Vos comptes</h2>
                <div className="account-grid">
                    {accounts.map((a) => <AccountCard key={a.id} account={a} />)}
                </div>
            </section>

            <div className="two-col">
                <TransactionList transactions={transactions} />
                <SpendingDonut transactions={transactions} />
            </div>
        </div>
    );
}
