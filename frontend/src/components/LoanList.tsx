import type { CSSProperties } from 'react';
import { Amount } from './Amount';
import { ProjectionChart } from './ProjectionChart';
import { projeterCapitalRestant } from '../lib/amortissement';
import { formatCentimes, formatPourcentage, masquerIdentifiant } from '../lib/format';
import type { Loan } from '../types/Account';
import './LoanList.css';

const moisAnnee = new Intl.DateTimeFormat('fr-FR', { month: 'long', year: 'numeric' });

interface LoanListProps {
    loans: Loan[] | null;
    error: string | null;
    vide?: string;
}

export function LoanList({ loans, error, vide = 'Aucun prêt en cours.' }: Readonly<LoanListProps>) {
    if (error && !loans) {
        return (
            <div className="panel-message" role="alert">
                <p className="panel-message-title">Impossible de charger les prêts</p>
                <p>{error}. Le serveur est peut-être en cours de démarrage, réessayez dans un instant.</p>
            </div>
        );
    }
    if (!loans) {
        return (
            <div className="loan-list" aria-busy="true" aria-label="Chargement des prêts">
                <div className="loan-card loan-skeleton" />
            </div>
        );
    }
    if (loans.length === 0) return <p className="empty-state">{vide}</p>;

    return (
        <div className="loan-list">
            {loans.map((loan, i) => (
                <LoanCard key={loan.id} loan={loan} index={i} />
            ))}
        </div>
    );
}

function LoanCard({ loan, index }: Readonly<{ loan: Loan; index: number }>) {
    const projection = projeterCapitalRestant(loan.montantRestantCentimes, loan.mensualiteCentimes, loan.tauxInteretPourMille);
    const dateDebut = new Date(loan.dateDebut);

    return (
        <article className="loan-card" style={{ '--delay': `${index * 80}ms` } as CSSProperties}>
            <div className="loan-facts">
                <header className="loan-head">
                    <h2 className="loan-holder">
                        {loan.clientPrenom} {loan.clientNom}
                    </h2>
                    <p className="loan-ref">
                        Prêt <span className="mono">{masquerIdentifiant(loan.id)}</span> · depuis {moisAnnee.format(dateDebut)}
                    </p>
                </header>

                <div className="loan-balance">
                    <p className="loan-label">Capital restant dû</p>
                    <Amount centimes={loan.montantRestantCentimes} className="loan-remaining" />
                    <div className="meter" role="progressbar" aria-valuenow={loan.progression} aria-valuemin={0} aria-valuemax={100} aria-label="Capital remboursé">
                        <span style={{ width: `${loan.progression}%` }} />
                    </div>
                    <p className="loan-progress">
                        <span className="mono">{loan.progression} %</span> remboursés sur {formatCentimes(loan.montantInitialCentimes)}
                    </p>
                </div>

                <dl className="loan-meta">
                    <div>
                        <dt>Mensualité</dt>
                        <dd className="amount">{formatCentimes(loan.mensualiteCentimes)}</dd>
                    </div>
                    <div>
                        <dt>Taux nominal</dt>
                        <dd className="amount">{formatPourcentage(loan.tauxInteretPourMille / 1000)}</dd>
                    </div>
                    <div>
                        <dt>Fin estimée</dt>
                        <dd>{projection ? moisAnnee.format(projection.fin) : '—'}</dd>
                    </div>
                    <div>
                        <dt>Intérêts restants</dt>
                        <dd className="amount">{projection ? `≈ ${formatCentimes(projection.interetsCentimes)}` : '—'}</dd>
                    </div>
                </dl>
            </div>

            <div className="loan-chart">
                {projection ? (
                    <ProjectionChart points={projection.points} titre="Projection du capital restant dû" />
                ) : (
                    <p className="empty-state">La mensualité ne couvre pas les intérêts : projection impossible.</p>
                )}
                <p className="loan-footnote">
                    Estimation à mensualité et taux constants, à partir du capital restant dû actuel.
                </p>
            </div>
        </article>
    );
}
