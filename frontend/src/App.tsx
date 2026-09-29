import { useState } from 'react';
import { Toaster } from 'react-hot-toast';
import { AccountsView } from './components/AccountsView';
import { CardsView } from './components/CardsView';
import { LoanList } from './components/LoanList';
import { Navigation, type View } from './components/Navigation';
import { Overview } from './components/Overview';
import { StatusMessage } from './components/StatusMessage';
import { TransferForm } from './components/TransferForm';
import { useApi } from './hooks/useApi';
import { formatLongDate, initials } from './lib/format';
import type { Account } from './types/Account';
import './App.css';

const TITLES: Record<View, string> = {
    overview: 'Tableau de bord',
    accounts: 'Mes comptes',
    transfer: 'Effectuer un virement',
    cards: 'Mes cartes',
    loans: 'Mes prêts',
};

function App() {
    const [activeView, setActiveView] = useState<View>('overview');
    const [refreshKey, setRefreshKey] = useState(0);
    const { data: accounts, loading, waking, error } = useApi<Account[]>('/api/accounts', refreshKey);

    const holder = accounts?.[0];
    const greeting = holder ? `Bonjour ${holder.clientPrenom}` : 'Bonjour';

    const renderView = () => {
        if (activeView === 'loans') return <LoanList />;
        if (!accounts) return <StatusMessage loading={loading} waking={waking} error={error} what="vos comptes" />;
        switch (activeView) {
            case 'accounts': return <AccountsView accounts={accounts} />;
            case 'transfer':
                return <TransferForm accounts={accounts} onTransferSuccess={() => setRefreshKey((k) => k + 1)} />;
            case 'cards': return <CardsView accounts={accounts} />;
            default: return <Overview accounts={accounts} onNavigate={setActiveView} />;
        }
    };

    return (
        <div className="app-shell">
            <Toaster
                position="top-right"
                toastOptions={{
                    style: { background: '#12172a', color: '#eef1fb', border: '1px solid rgba(255,255,255,0.12)' },
                }}
            />
            <Navigation active={activeView} onChange={setActiveView} />
            <main className="main-content">
                <header className="top-bar">
                    <div>
                        <p className="greeting">{greeting}</p>
                        <p className="date">{formatLongDate(new Date())}</p>
                    </div>
                    {holder && <span className="avatar" title={`${holder.clientPrenom} ${holder.clientNom}`}>
                        {initials(holder.clientPrenom, holder.clientNom)}
                    </span>}
                </header>
                <h1>{TITLES[activeView]}</h1>
                {renderView()}
            </main>
        </div>
    );
}

export default App;
