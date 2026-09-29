import { Icon, type IconName } from './Icon';

export type View = 'overview' | 'accounts' | 'transfer' | 'cards' | 'loans';

const ITEMS: { view: View; label: string; icon: IconName }[] = [
    { view: 'overview', label: 'Tableau de bord', icon: 'home' },
    { view: 'accounts', label: 'Comptes', icon: 'wallet' },
    { view: 'transfer', label: 'Virements', icon: 'swap' },
    { view: 'cards', label: 'Cartes', icon: 'card' },
    { view: 'loans', label: 'Prêts', icon: 'loan' },
];

interface NavigationProps {
    active: View;
    onChange: (view: View) => void;
}

export function Navigation({ active, onChange }: Readonly<NavigationProps>) {
    return (
        <aside className="sidebar">
            <div className="brand">
                <span className="brand-mark" aria-hidden="true">J</span>
                <div>
                    <p className="brand-name">Java Bank</p>
                    <p className="brand-tagline">Banque en ligne</p>
                </div>
            </div>
            <nav aria-label="Navigation principale">
                {ITEMS.map((item) => (
                    <button
                        key={item.view}
                        className={active === item.view ? 'nav-item active' : 'nav-item'}
                        aria-current={active === item.view ? 'page' : undefined}
                        onClick={() => onChange(item.view)}
                    >
                        <Icon name={item.icon} />
                        <span>{item.label}</span>
                    </button>
                ))}
            </nav>
            <div className="sidebar-footer">
                <Icon name="shield" size={18} />
                <p>Connexion sécurisée</p>
            </div>
        </aside>
    );
}
