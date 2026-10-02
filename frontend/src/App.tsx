import { useState } from 'react';
import { Toaster } from 'react-hot-toast';
import { AccountList } from './components/AccountList';
import { TransferForm } from './components/TransferForm';
import { LoanList } from './components/LoanList';
import { Icon, type IconName } from './components/Icon';
import { formatDateLongue } from './lib/format';
import type { Account } from './types/Account';
import './App.css';
import './legacy.css';

type View = 'overview' | 'transfer' | 'loans';

const VIEWS: { id: View; label: string; title: string; icon: IconName }[] = [
    { id: 'overview', label: "Vue d'ensemble", title: "Vue d'ensemble", icon: 'overview' },
    { id: 'transfer', label: 'Virement', title: 'Effectuer un virement', icon: 'transfer' },
    { id: 'loans', label: 'Prêts', title: 'Vos prêts', icon: 'loans' },
];

function App() {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [refreshKey, setRefreshKey] = useState(0);
    const [activeView, setActiveView] = useState<View>('overview');

    const current = VIEWS.find((view) => view.id === activeView) ?? VIEWS[0];
    const greeting = accounts.length > 0 ? `Bonjour, ${accounts[0].clientPrenom}` : 'Bonjour';

    return (
        <div className="app-shell">
            <Toaster
                position="top-right"
                toastOptions={{ className: 'toast', success: { iconTheme: { primary: '#1F7A5C', secondary: '#fff' } } }}
            />
            <aside className="sidebar">
                <div className="brand">
                    <span className="brand-mark" aria-hidden="true">JB</span>
                    <span className="brand-name">Java Bank</span>
                </div>
                <nav aria-label="Navigation principale">
                    {VIEWS.map((view) => (
                        <button
                            key={view.id}
                            className={activeView === view.id ? 'nav-item active' : 'nav-item'}
                            aria-current={activeView === view.id ? 'page' : undefined}
                            onClick={() => setActiveView(view.id)}
                        >
                            <Icon name={view.icon} />
                            <span>{view.label}</span>
                        </button>
                    ))}
                </nav>
                <p className="sidebar-foot">Banque privée · Espace client</p>
            </aside>

            <main className="main-content">
                <header className="page-header">
                    <p className="eyebrow">
                        <span>{greeting}</span>
                        <span className="eyebrow-sep" aria-hidden="true" />
                        <span className="eyebrow-date">{formatDateLongue(new Date())}</span>
                    </p>
                    <h1 key={activeView} className="page-title">{current.title}</h1>
                </header>

                <div key={activeView} className="view">
                    <div hidden={activeView !== 'overview'}>
                        <AccountList key={refreshKey} onAccountsLoaded={setAccounts} />
                    </div>
                    {activeView === 'transfer' && (
                        <TransferForm accounts={accounts} onTransferSuccess={() => setRefreshKey((k) => k + 1)} />
                    )}
                    {activeView === 'loans' && <LoanList />}
                </div>
            </main>
        </div>
    );
}

export default App;
