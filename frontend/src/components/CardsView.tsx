import { useState } from 'react';
import type { Account } from '../types/Account';
import { Icon, type IconName } from './Icon';

function Toggle({ icon, label, hint, checked, onChange }: Readonly<{
    icon: IconName;
    label: string;
    hint: string;
    checked: boolean;
    onChange: (value: boolean) => void;
}>) {
    return (
        <div className="setting">
            <span className="setting-icon"><Icon name={icon} size={18} /></span>
            <div className="setting-text">
                <p id={`lbl-${label}`}>{label}</p>
                <p className="setting-hint">{hint}</p>
            </div>
            <button role="switch" aria-checked={checked} aria-labelledby={`lbl-${label}`}
                    className={checked ? 'switch on' : 'switch'} onClick={() => onChange(!checked)}>
                <span className="switch-knob" />
            </button>
        </div>
    );
}

export function CardsView({ accounts }: Readonly<{ accounts: Account[] }>) {
    const holder = accounts[0];
    const [frozen, setFrozen] = useState(false);
    const [online, setOnline] = useState(true);
    const [contactless, setContactless] = useState(true);
    const [limit, setLimit] = useState(1500);

    const name = holder ? `${holder.clientPrenom} ${holder.clientNom}`.toUpperCase() : 'TITULAIRE';

    return (
        <div className="two-col cards-layout">
            <div className="stack">
                <div className={frozen ? 'bank-card frozen' : 'bank-card'} aria-label="Carte bancaire virtuelle">
                    <div className="bank-card-top">
                        <span className="bank-card-brand">Java Bank</span>
                        <Icon name="wifi" size={24} />
                    </div>
                    <span className="chip" aria-hidden="true" />
                    <p className="bank-card-number">•••• •••• •••• 4821</p>
                    <div className="bank-card-bottom">
                        <div>
                            <p className="bank-card-label">Titulaire</p>
                            <p>{name}</p>
                        </div>
                        <div>
                            <p className="bank-card-label">Expire</p>
                            <p>09/29</p>
                        </div>
                    </div>
                    {frozen && <p className="frozen-banner"><Icon name="lock" size={16} /> Carte bloquée</p>}
                </div>
                <p className="demo-note"><span className="badge">démo</span> Carte fictive : aucune donnée bancaire réelle.</p>
            </div>

            <section className="panel" aria-labelledby="card-settings">
                <header className="panel-header"><h2 id="card-settings">Paramètres de la carte</h2><span className="badge">démo</span></header>
                <Toggle icon="lock" label="Bloquer la carte" hint="Refuse tous les paiements immédiatement" checked={frozen} onChange={setFrozen} />
                <Toggle icon="globe" label="Paiements en ligne" hint="Achats sur Internet et à l'étranger" checked={online} onChange={setOnline} />
                <Toggle icon="wifi" label="Sans contact" hint="Paiement rapide jusqu'à 50 €" checked={contactless} onChange={setContactless} />
                <label className="slider">
                    <span>Plafond mensuel <strong>{limit.toLocaleString('fr-FR')} €</strong></span>
                    <input type="range" min={500} max={5000} step={100} value={limit}
                           onChange={(e) => setLimit(Number(e.target.value))} />
                </label>
            </section>
        </div>
    );
}
