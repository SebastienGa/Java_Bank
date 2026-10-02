import { Amount } from './Amount';
import { BankCard } from './BankCard';
import { Icon } from './Icon';
import { LoanList } from './LoanList';
import { useApi } from '../hooks/useApi';
import type { ClientDashboard } from '../types/Account';
import './ClientView.css';

interface ClientViewProps {
    clientId: string;
    refreshKey: number;
    onBack: () => void;
    onNewTransfer: () => void;
    tintFor: (accountId: string) => string;
}

export function ClientView({ clientId, refreshKey, onBack, onNewTransfer, tintFor }: Readonly<ClientViewProps>) {
    const { data: client, error } = useApi<ClientDashboard>(`/api/clients/${clientId}`, refreshKey);

    const retour = (
        <button type="button" className="back-link" onClick={onBack}>
            <Icon name="back" />
            Vue d'ensemble
        </button>
    );

    if (error && !client) {
        return (
            <div className="client">
                {retour}
                <div className="panel-message" role="alert">
                    <p className="panel-message-title">Impossible de charger la fiche client</p>
                    <p>{error}.</p>
                </div>
            </div>
        );
    }
    if (!client) {
        return (
            <div className="client" aria-busy="true" aria-label="Chargement de la fiche client">
                {retour}
                <section className="deck">
                    <div className="skeleton skeleton-total" />
                </section>
            </div>
        );
    }

    const avoirs = client.comptes.reduce((sum, compte) => sum + compte.soldeCentimes, 0);
    const encours = client.prets.reduce((sum, pret) => sum + pret.montantRestantCentimes, 0);
    const initiales = `${client.prenom.charAt(0)}${client.nom.charAt(0)}`.toUpperCase();

    return (
        <div className="client">
            {retour}
            <section className="deck client-deck" aria-labelledby="client-name">
                <div className="client-identity">
                    <span className="client-avatar" aria-hidden="true">
                        {initiales}
                    </span>
                    <div>
                        <p className="deck-label">Titulaire</p>
                        <h2 id="client-name" className="client-name">
                            {client.prenom} {client.nom}
                        </h2>
                    </div>
                    <button type="button" className="btn-glow client-action" onClick={onNewTransfer}>
                        Nouveau virement
                        <Icon name="arrow" />
                    </button>
                </div>

                <dl className="client-figures">
                    <div>
                        <dt>Avoirs</dt>
                        <dd>
                            <Amount centimes={avoirs} />
                        </dd>
                    </div>
                    <div>
                        <dt>Encours de crédit</dt>
                        <dd>
                            <Amount centimes={encours} />
                        </dd>
                    </div>
                    <div>
                        <dt>Position nette</dt>
                        <dd>
                            <Amount centimes={avoirs - encours} />
                        </dd>
                    </div>
                </dl>

                {client.comptes.length > 0 ? (
                    <div className="card-row">
                        {client.comptes.map((compte, i) => (
                            <BankCard key={compte.id} account={compte} index={i} tint={tintFor(compte.id)} />
                        ))}
                    </div>
                ) : (
                    <p className="deck-sub">Aucun compte ouvert.</p>
                )}
            </section>

            <section className="client-section" aria-labelledby="client-loans">
                <h2 id="client-loans" className="section-title">
                    Prêts
                </h2>
                <LoanList loans={client.prets} error={null} vide="Aucun prêt en cours pour ce client." />
            </section>
        </div>
    );
}
