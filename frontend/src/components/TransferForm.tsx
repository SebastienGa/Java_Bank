import { useState, type FormEvent } from 'react';
import toast from 'react-hot-toast';
import type { Account, ApiError, TransferRequest } from '../types/Account';

interface TransferFormProps {
    accounts: Account[];
    onTransferSuccess: () => void;
}

export function TransferForm({ accounts, onTransferSuccess }: TransferFormProps) {
    const [sourceId, setSourceId] = useState('');
    const [destinationId, setDestinationId] = useState('');
    const [montantEuros, setMontantEuros] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();

        if (!sourceId || !destinationId || !montantEuros) {
            toast.error('Tous les champs sont requis.');
            return;
        }

        const montantCentimes = Math.round(parseFloat(montantEuros) * 100);
        if (isNaN(montantCentimes) || montantCentimes <= 0) {
            toast.error('Montant invalide.');
            return;
        }

        const body: TransferRequest = { destinationAccountId: destinationId, montantCentimes };
        setSubmitting(true);

        try {
            const response = await fetch(`http://localhost:8080/api/accounts/${sourceId}/transfer`, {
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
        <div className="transfer-card">
            <form onSubmit={handleSubmit}>
                <h2>Effectuer un virement</h2>

                <div className="form-row">
                    <label htmlFor="source">Compte source</label>
                    <select id="source" value={sourceId} onChange={(e) => setSourceId(e.target.value)}>
                        <option value="">-- Sélectionner --</option>
                        {accounts.map((a) => (
                            <option key={a.id} value={a.id}>
                                {a.clientPrenom} {a.clientNom} ({(a.soldeCentimes / 100).toFixed(2)} €)
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-row">
                    <label htmlFor="destination">Compte destinataire</label>
                    <select id="destination" value={destinationId} onChange={(e) => setDestinationId(e.target.value)}>
                        <option value="">-- Sélectionner --</option>
                        {accounts.map((a) => (
                            <option key={a.id} value={a.id}>
                                {a.clientPrenom} {a.clientNom}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-row">
                    <label htmlFor="montant">Montant (€)</label>
                    <input
                        id="montant"
                        type="number"
                        step="0.01"
                        min="0.01"
                        value={montantEuros}
                        onChange={(e) => setMontantEuros(e.target.value)}
                    />
                </div>

                <button type="submit" className="transfer-submit" disabled={submitting}>
                    {submitting ? 'Envoi...' : 'Virer'}
                </button>
            </form>
        </div>
    );
}