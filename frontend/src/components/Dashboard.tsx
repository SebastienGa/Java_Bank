import { Amount } from './Amount';
import './Dashboard.css';
import { BankCard } from './BankCard';
import { Icon } from './Icon';
import { formatCentimes, formatPourcentage } from '../lib/format';
import { teinteSerie } from '../lib/series';
import type { Account, Loan } from '../types/Account';

interface DashboardProps {
    accounts: Account[] | null;
    loans: Loan[] | null;
    error: string | null;
    onNewTransfer: () => void;
    onOpenClient?: (clientId: string) => void;
}

export function Dashboard({ accounts, loans, error, onNewTransfer, onOpenClient }: Readonly<DashboardProps>) {
    if (error && !accounts) {
        return (
            <div className="panel-message" role="alert">
                <p className="panel-message-title">Impossible de charger vos comptes</p>
                <p>{error}. Le serveur est peut-être en cours de démarrage, réessayez dans un instant.</p>
            </div>
        );
    }
    if (!accounts) return <DashboardSkeleton />;

    const total = accounts.reduce((sum, account) => sum + account.soldeCentimes, 0);
    const titulaires = new Set(accounts.map((a) => `${a.clientPrenom} ${a.clientNom}`)).size;
    const part = (account: Account) => (total > 0 ? account.soldeCentimes / total : 0);

    return (
        <div className="dashboard">
            <section className="deck" aria-labelledby="deck-title">
                <div className="deck-head">
                    <div>
                        <p id="deck-title" className="deck-label">Avoirs consolidés</p>
                        <Amount centimes={total} className="deck-total" />
                        <p className="deck-sub">
                            {accounts.length} compte{accounts.length > 1 ? 's' : ''} · {titulaires} titulaire
                            {titulaires > 1 ? 's' : ''}
                        </p>
                    </div>
                    <button className="btn-glow" onClick={onNewTransfer}>
                        Nouveau virement
                        <Icon name="arrow" />
                    </button>
                </div>

                <div className="allocation">
                    <div className="allocation-bar" role="img" aria-label="Répartition des avoirs par compte">
                        {accounts.map((account, i) => (
                            <span
                                key={account.id}
                                className="allocation-segment"
                                data-tooltip={`${account.clientPrenom} ${account.clientNom} · ${formatCentimes(account.soldeCentimes)} · ${formatPourcentage(part(account))}`}
                                style={{ flexGrow: account.soldeCentimes, background: teinteSerie(i) }}
                            />
                        ))}
                    </div>
                    <ul className="allocation-legend">
                        {accounts.map((account, i) => (
                            <li key={account.id}>
                                <span className="dot" style={{ background: teinteSerie(i) }} />
                                {account.clientPrenom} {account.clientNom}
                                <span className="allocation-share">{formatPourcentage(part(account))}</span>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="card-row">
                    {accounts.map((account, i) => (
                        <BankCard
                            key={account.id}
                            account={account}
                            index={i}
                            tint={teinteSerie(i)}
                            onOpen={onOpenClient && (() => onOpenClient(account.clientId))}
                        />
                    ))}
                </div>
            </section>

            <LoansSummary loans={loans} />
        </div>
    );
}

function LoansSummary({ loans }: Readonly<{ loans: Loan[] | null }>) {
    if (!loans) return null;
    const encours = loans.reduce((sum, loan) => sum + loan.montantRestantCentimes, 0);
    const mensualites = loans.reduce((sum, loan) => sum + loan.mensualiteCentimes, 0);
    const initial = loans.reduce((sum, loan) => sum + loan.montantInitialCentimes, 0);
    const rembourse = initial > 0 ? 1 - encours / initial : 0;

    return (
        <section className="stats" aria-label="Synthèse des crédits">
            <div className="stat">
                <p className="stat-label">Encours de crédit</p>
                <Amount centimes={encours} className="stat-value" />
                <p className="stat-hint">
                    {loans.length} prêt{loans.length > 1 ? 's' : ''} en cours
                </p>
            </div>
            <div className="stat">
                <p className="stat-label">Mensualités cumulées</p>
                <Amount centimes={mensualites} className="stat-value" />
                <p className="stat-hint">prélevées chaque mois</p>
            </div>
            <div className="stat">
                <p className="stat-label">Capital remboursé</p>
                <span className="amount stat-value">{formatPourcentage(rembourse)}</span>
                <div className="meter" aria-hidden="true">
                    <span style={{ width: `${rembourse * 100}%` }} />
                </div>
                <p className="stat-hint">sur {formatCentimes(initial)} empruntés</p>
            </div>
        </section>
    );
}

function DashboardSkeleton() {
    return (
        <div className="dashboard" aria-busy="true" aria-label="Chargement des comptes">
            <section className="deck">
                <div className="skeleton skeleton-line" style={{ width: 140 }} />
                <div className="skeleton skeleton-total" />
                <div className="card-row">
                    {[0, 1, 2].map((i) => (
                        <div key={i} className="bank-card skeleton" />
                    ))}
                </div>
            </section>
        </div>
    );
}
