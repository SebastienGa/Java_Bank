import { useState } from 'react';
import type { Loan } from '../types/Account';
import { formatEuros } from '../lib/format';
import { StatusMessage } from './StatusMessage';
import { useApi } from '../hooks/useApi';

/** Mensualité d'un prêt amortissable classique (formule standard, taux annuel en %). */
function monthlyPayment(principal: number, annualRatePercent: number, months: number): number {
    const monthlyRate = annualRatePercent / 100 / 12;
    if (monthlyRate === 0) return principal / months;
    return (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
}

function LoanSimulator() {
    const [amount, setAmount] = useState(15000);
    const [months, setMonths] = useState(48);
    const [rate, setRate] = useState(3.9);

    const payment = monthlyPayment(amount, rate, months);
    const totalCost = payment * months - amount;

    return (
        <section className="panel" aria-labelledby="sim-title">
            <header className="panel-header">
                <h2 id="sim-title">Simulateur de prêt</h2>
                <span className="badge">indicatif</span>
            </header>
            <div className="sim-grid">
                <label className="slider">
                    <span>Montant <strong>{formatEuros(amount * 100)}</strong></span>
                    <input type="range" min={1000} max={50000} step={500} value={amount}
                           onChange={(e) => setAmount(Number(e.target.value))} />
                </label>
                <label className="slider">
                    <span>Durée <strong>{months} mois</strong></span>
                    <input type="range" min={12} max={120} step={6} value={months}
                           onChange={(e) => setMonths(Number(e.target.value))} />
                </label>
                <label className="slider">
                    <span>Taux annuel <strong>{rate.toFixed(1).replace('.', ',')} %</strong></span>
                    <input type="range" min={0.5} max={9} step={0.1} value={rate}
                           onChange={(e) => setRate(Number(e.target.value))} />
                </label>
            </div>
            <div className="sim-result">
                <div>
                    <p className="eyebrow">Mensualité estimée</p>
                    <p className="sim-payment">{formatEuros(Math.round(payment * 100))}</p>
                </div>
                <div>
                    <p className="eyebrow">Coût total du crédit</p>
                    <p className="sim-cost">{formatEuros(Math.round(totalCost * 100))}</p>
                </div>
            </div>
        </section>
    );
}

export function LoanList() {
    const { data: loans, loading, waking, error } = useApi<Loan[]>('/api/loans');
    const status = <StatusMessage loading={loading} waking={waking} error={error} what="vos prêts" />;

    return (
        <div className="stack">
            {loans ? (
                <div className="loan-grid">
                    {loans.map((loan) => (
                        <article className="panel loan-card" key={loan.id}>
                            <div className="loan-head">
                                <p className="account-owner">{loan.clientPrenom} {loan.clientNom}</p>
                                <span className="badge badge-live">En cours</span>
                            </div>
                            <p className="eyebrow">Capital restant dû</p>
                            <p className="loan-remaining">{formatEuros(loan.montantRestantCentimes)}</p>
                            <p className="loan-initial">sur {formatEuros(loan.montantInitialCentimes)} empruntés</p>
                            <div className="progress-track" role="progressbar" aria-valuenow={loan.progression}
                                 aria-valuemin={0} aria-valuemax={100} aria-label="Remboursement">
                                <div className="progress-fill" style={{ width: `${loan.progression}%` }} />
                            </div>
                            <div className="loan-meta">
                                <span>Mensualité <strong>{formatEuros(loan.mensualiteCentimes)}</strong></span>
                                <span>Taux <strong>{(loan.tauxInteretPourMille / 10).toFixed(1).replace('.', ',')} %</strong></span>
                                <span>{Math.round(loan.progression)} % remboursé</span>
                            </div>
                        </article>
                    ))}
                </div>
            ) : status}
            <LoanSimulator />
        </div>
    );
}
