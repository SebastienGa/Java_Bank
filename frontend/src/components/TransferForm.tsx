import { useState, type FormEvent } from 'react';
import toast from 'react-hot-toast';
import { Amount } from './Amount';
import { Icon } from './Icon';
import { formatCentimes, masquerIdentifiant } from '../lib/format';
import { teinteSerie } from '../lib/series';
import type { Account, ApiError, TransferRequest } from '../types/Account';
import './TransferForm.css';

const MONTANT_VALIDE = /^\d+([.,]\d{0,2})?$/;
const MONTANTS_RAPIDES = [5000, 10000, 25000];

interface TransferFormProps {
    accounts: Account[];
    onTransferSuccess: () => void;
}

interface Confirmation {
    montantCentimes: number;
    source: Account;
    destination: Account;
}

function parseMontant(saisie: string): number | null {
    if (!MONTANT_VALIDE.test(saisie)) return null;
    return Math.round(Number.parseFloat(saisie.replace(',', '.')) * 100);
}

function aideMontant(source: Account | undefined, saisieInvalide: boolean, soldeInsuffisant: boolean): string {
    if (saisieInvalide) return 'Saisissez un montant positif, deux décimales au plus.';
    if (!source) return 'Choisissez d’abord le compte à débiter.';
    if (soldeInsuffisant) return `Solde insuffisant : ${formatCentimes(source.soldeCentimes)} disponibles.`;
    return `Disponible : ${formatCentimes(source.soldeCentimes)}`;
}

export function TransferForm({ accounts, onTransferSuccess }: Readonly<TransferFormProps>) {
    const [sourceId, setSourceId] = useState('');
    const [destinationId, setDestinationId] = useState('');
    const [saisie, setSaisie] = useState('');
    const [submitting, setSubmitting] = useState(false);
    const [confirmation, setConfirmation] = useState<Confirmation | null>(null);

    const source = accounts.find((a) => a.id === sourceId);
    const destination = accounts.find((a) => a.id === destinationId);
    const montantCentimes = parseMontant(saisie);
    const montantValide = montantCentimes !== null && montantCentimes > 0;
    const soldeInsuffisant = source !== undefined && montantValide && montantCentimes > source.soldeCentimes;
    const pret = source && destination && montantValide && !soldeInsuffisant;

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (!source || !destination || !montantValide) {
            toast.error('Choisissez deux comptes et un montant valide.');
            return;
        }

        const body: TransferRequest = { destinationAccountId: destination.id, montantCentimes };
        setSubmitting(true);
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/accounts/${source.id}/transfer`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(body),
            });

            if (response.status === 204) {
                toast.success('Virement effectué avec succès.');
                setConfirmation({ montantCentimes, source, destination });
                setSourceId('');
                setDestinationId('');
                setSaisie('');
                onTransferSuccess();
            } else {
                const error: ApiError = await response.json();
                toast.error(error.message);
            }
        } catch {
            toast.error('Erreur réseau — le serveur est-il démarré ?');
        } finally {
            setSubmitting(false);
        }
    };

    const choisirSource = (id: string) => {
        setConfirmation(null);
        setSourceId(id);
        if (id === destinationId) setDestinationId('');
    };

    return (
        <form className="transfer" onSubmit={handleSubmit} noValidate>
            <div className="transfer-steps">
                <fieldset className="transfer-step">
                    <legend>
                        <span className="step-index">01</span> Compte à débiter
                    </legend>
                    <AccountPicker name="source" accounts={accounts} selectedId={sourceId} onSelect={choisirSource} />
                </fieldset>

                <fieldset className="transfer-step">
                    <legend>
                        <span className="step-index">02</span> Compte à créditer
                    </legend>
                    <AccountPicker
                        name="destination"
                        accounts={accounts}
                        selectedId={destinationId}
                        disabledId={sourceId}
                        onSelect={(id) => {
                            setConfirmation(null);
                            setDestinationId(id);
                        }}
                    />
                </fieldset>

                <div className="transfer-step">
                    <label className="step-legend" htmlFor="montant">
                        <span className="step-index">03</span> Montant
                    </label>
                    <div className={soldeInsuffisant ? 'amount-field invalid' : 'amount-field'}>
                        <input
                            id="montant"
                            inputMode="decimal"
                            autoComplete="off"
                            placeholder="0,00"
                            value={saisie}
                            aria-invalid={soldeInsuffisant || (saisie !== '' && !montantValide)}
                            aria-describedby="montant-aide"
                            onChange={(e) => {
                                setConfirmation(null);
                                setSaisie(e.target.value.trim());
                            }}
                        />
                        <span className="amount-suffix" aria-hidden="true">€</span>
                    </div>
                    <div className="quick-amounts">
                        {MONTANTS_RAPIDES.map((centimes) => (
                            <button
                                key={centimes}
                                type="button"
                                className="quick-amount"
                                onClick={() => {
                                    setConfirmation(null);
                                    setSaisie(String(centimes / 100));
                                }}
                            >
                                {formatCentimes(centimes)}
                            </button>
                        ))}
                    </div>
                    <p id="montant-aide" className={soldeInsuffisant ? 'field-hint error' : 'field-hint'}>
                        {aideMontant(source, saisie !== '' && !montantValide, soldeInsuffisant)}
                    </p>
                </div>
            </div>

            <aside className="transfer-summary" aria-live="polite">
                {confirmation ? (
                    <TransferDone confirmation={confirmation} onReset={() => setConfirmation(null)} />
                ) : (
                    <>
                        <p className="summary-label">Récapitulatif</p>
                        <Amount centimes={montantValide ? montantCentimes : 0} className="summary-amount" />

                        <div className="route">
                            <RouteEnd label="De" account={source} delta={montantValide && !soldeInsuffisant ? -montantCentimes : 0} />
                            <div className={pret ? 'route-flow active' : 'route-flow'} aria-hidden="true">
                                <span />
                            </div>
                            <RouteEnd label="Vers" account={destination} delta={pret ? montantCentimes : 0} />
                        </div>

                        <button type="submit" className="btn-glow summary-submit" disabled={!pret || submitting}>
                            {submitting ? 'Envoi en cours…' : 'Confirmer le virement'}
                            {!submitting && <Icon name="arrow" />}
                        </button>
                        <p className="summary-note">Virement interne immédiat, sans frais.</p>
                    </>
                )}
            </aside>
        </form>
    );
}

interface AccountPickerProps {
    name: string;
    accounts: Account[];
    selectedId: string;
    disabledId?: string;
    onSelect: (id: string) => void;
}

function AccountPicker({ name, accounts, selectedId, disabledId, onSelect }: Readonly<AccountPickerProps>) {
    if (accounts.length === 0) return <p className="field-hint">Chargement des comptes…</p>;
    return (
        <div className="picker">
            {accounts.map((account, i) => (
                <label
                    key={account.id}
                    className={['picker-option', account.id === selectedId && 'selected', account.id === disabledId && 'disabled']
                        .filter(Boolean)
                        .join(' ')}
                >
                    <input
                        type="radio"
                        name={name}
                        value={account.id}
                        checked={account.id === selectedId}
                        disabled={account.id === disabledId}
                        onChange={() => onSelect(account.id)}
                    />
                    <span className="picker-dot" style={{ background: teinteSerie(i) }} aria-hidden="true" />
                    <span className="picker-text">
                        <span className="picker-name">
                            {account.clientPrenom} {account.clientNom}
                        </span>
                        <span className="picker-number">{masquerIdentifiant(account.id)}</span>
                    </span>
                    <Amount centimes={account.soldeCentimes} className="picker-balance" />
                </label>
            ))}
        </div>
    );
}

function RouteEnd({ label, account, delta }: Readonly<{ label: string; account?: Account; delta: number }>) {
    return (
        <div className="route-end">
            <p className="route-label">{label}</p>
            {account ? (
                <>
                    <p className="route-name">
                        {account.clientPrenom} {account.clientNom}
                    </p>
                    <p className="route-balance">
                        Solde après : <Amount centimes={account.soldeCentimes + delta} />
                    </p>
                </>
            ) : (
                <p className="route-name placeholder">—</p>
            )}
        </div>
    );
}

function TransferDone({ confirmation, onReset }: Readonly<{ confirmation: Confirmation; onReset: () => void }>) {
    return (
        <div className="transfer-done">
            <svg className="done-check" viewBox="0 0 52 52" aria-hidden="true">
                <circle cx="26" cy="26" r="24" />
                <path d="M15 27l7 7 15-16" />
            </svg>
            <p className="summary-label">Virement effectué</p>
            <span className="amount summary-amount">{formatCentimes(confirmation.montantCentimes)}</span>
            <p className="done-route">
                {confirmation.source.clientPrenom} {confirmation.source.clientNom} → {confirmation.destination.clientPrenom}{' '}
                {confirmation.destination.clientNom}
            </p>
            <button type="button" className="btn-ghost" onClick={onReset}>
                Nouveau virement
            </button>
        </div>
    );
}
