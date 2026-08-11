import { useState } from 'react';
import { Toaster } from 'react-hot-toast';
import { AccountList } from './components/AccountList';
import { TransferForm } from './components/TransferForm';
import { LoanList } from './components/LoanList';
import type { Account } from './types/Account';
import './App.css';

type View = 'overview' | 'transfer' | 'loans';

function formatDate(date: Date): string {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}

function App() {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [refreshKey, setRefreshKey] = useState(0);
    const [activeView, setActiveView] = useState<View>('overview');

    const totalCentimes = accounts.reduce((sum, account) => sum + account.soldeCentimes, 0);
    const greeting = accounts.length > 0 ? `Bonjour, ${accounts[0].clientPrenom}` : 'Bonjour';

    return (
        <div className="app-shell">
            <Toaster position="top-right" />
            <div className="sidebar">
                <p className="brand">Java Bank</p>
                <p className="brand-tagline">Votre gestion de comptes, simplifiée</p>
                <nav>
                    <button
                        className={activeView === 'overview' ? 'active' : ''}
                        onClick={() => setActiveView('overview')}
                    >
                        Vue d'ensemble
                    </button>
                    <button
                        className={activeView === 'transfer' ? 'active' : ''}
                        onClick={() => setActiveView('transfer')}
                    >
                        Virement
                    </button>
                    <button
                        className={activeView === 'loans' ? 'active' : ''}
                        onClick={() => setActiveView('loans')}
                    >
                        Prêts
                    </button>
                </nav>
            </div>
            <div className="main-content">
                <div className="top-bar">
                    <span className="greeting">{greeting}</span>
                    <span className="date">{formatDate(new Date())}</span>
                </div>
                <h1>
                    {activeView === 'overview' && "Vue d'ensemble"}
                    {activeView === 'transfer' && 'Effectuer un virement'}
                    {activeView === 'loans' && 'Vos prêts'}
                </h1>
                {activeView === 'overview' && (
                    <div className="hero-balance">
                        <p className="label">Solde total</p>
                        <p className="amount">{(totalCentimes / 100).toFixed(2)}€</p>
                        <p className="sub">Réparti sur {accounts.length} comptes</p>
                    </div>
                )}
                <div style={{ display: activeView === 'overview' ? 'block' : 'none' }}>
                    <AccountList key={refreshKey} onAccountsLoaded={setAccounts} />
                </div>
                {activeView === 'transfer' && (
                    <TransferForm accounts={accounts} onTransferSuccess={() => setRefreshKey((k) => k + 1)} />
                )}
                {activeView === 'loans' && <LoanList />}
            </div>
        </div>
    );
}

export default App;