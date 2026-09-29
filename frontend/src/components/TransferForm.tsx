import { useState, type FormEvent } from 'react';
import toast from 'react-hot-toast';
import type { Account, ApiError, TransferRequest } from '../types/Account';
import { formatEuros } from '../lib/format';

interface TransferFormProps {
    accounts: Account[];
    onTransferSuccess: () => void;
}

export function TransferForm({ accounts, onTransferSuccess }: Readonly<TransferFormProps>) {
    const [sourceId, setSourceId] = useState('');
    const [destinationId, setDestinationId] = useState('');
    const [montantEuros, setMontantEuros] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const source = accounts.find((a) => a.id === sourceId);
    const destinations = accounts.filter((a) => a.id !== sourceId);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();

        if (!sourceId || !destinationId || !montantEuros) {
            toast.error('Tous les champs sont requis.');
            return;
        }

        const montantCentimes = Math.round(Number.parseFloat(montantEuros) * 100);
        if (Number.isNaN(montantCentimes) || montantCentimes <= 0) {
            toast.error('Montant invalide.');
            return;
        }

        const body: TransferRequest = { destinationAccountId: destinationId, montantCentimes };
        setSubmitting(true);

        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/accounts/${sourceId}/transfer`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(body),
            });

            if (response.status === 204) {
                toast.success('Virement effectué avec succès.');
                setSourceId('');
                setDestinationId('');
                setMontantEuros('');
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

    return (
        <form className="panel transfer" onSubmit={handleSubmit}>
            <div className="amount-field">
                <label htmlFor="montant" className="eyebrow">Montant du virement</label>
                <div className="amount-input">
                    <input
                        id="montant"
                        type="number"
                        inputMode="decimal"
                        step="0.01"
                        min="0.01"
                        placeholder="0,00"
                        value={montantEuros}
                        onChange={(e) => setMontantEuros(e.target.value)}
                    />
                    <span aria-hidden="true">€</span>
                </div>
                {source && <p className="field-hint">Disponible : {formatEuros(source.soldeCentimes)}</p>}
            </div>

            <div className="field">
                <label htmlFor="source">Depuis</label>
                <select id="source" value={sourceId} onChange={(e) => {
                    setSourceId(e.target.value);
                    if (e.target.value === destinationId) setDestinationId('');
                }}>
                    <option value="">Choisir le compte à débiter</option>
                    {accounts.map((a) => (
                        <option key={a.id} value={a.id}>
                            {a.clientPrenom} {a.clientNom} — {formatEuros(a.soldeCentimes)}
                        </option>
                    ))}
                </select>
            </div>

            <div className="field">
                <label htmlFor="destination">Vers</label>
                <select id="destination" value={destinationId} onChange={(e) => setDestinationId(e.target.value)}>
                    <option value="">Choisir le bénéficiaire</option>
                    {destinations.map((a) => (
                        <option key={a.id} value={a.id}>{a.clientPrenom} {a.clientNom}</option>
                    ))}
                </select>
            </div>

            <button type="submit" className="btn btn-primary btn-block" disabled={submitting}>
                {submitting ? 'Envoi en cours…' : 'Valider le virement'}
            </button>
        </form>
    );
}
