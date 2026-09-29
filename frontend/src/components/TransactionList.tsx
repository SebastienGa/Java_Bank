import type { DemoTransaction } from '../data/demo';
import { formatEuros, formatShortDate } from '../lib/format';
import { Icon } from './Icon';

export function TransactionList({ transactions }: Readonly<{ transactions: DemoTransaction[] }>) {
    return (
        <section className="panel" aria-labelledby="tx-title">
            <header className="panel-header">
                <h2 id="tx-title">Dernières opérations</h2>
                <span className="badge">démo</span>
            </header>
            <ul className="tx-list">
                {transactions.map((t) => {
                    const credit = t.amountCentimes > 0;
                    return (
                        <li key={t.id} className="tx-row">
                            <span className={credit ? 'tx-icon credit' : 'tx-icon'}>
                                <Icon name={credit ? 'down' : 'up'} size={18} />
                            </span>
                            <div className="tx-main">
                                <p className="tx-label">{t.label}</p>
                                <p className="tx-meta">{t.category} · {formatShortDate(t.date)}</p>
                            </div>
                            <p className={credit ? 'tx-amount credit' : 'tx-amount'}>
                                {credit ? '+' : ''}{formatEuros(t.amountCentimes)}
                            </p>
                        </li>
                    );
                })}
            </ul>
        </section>
    );
}
