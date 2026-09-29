const PATHS = {
    home: 'M3 11l9-8 9 8M5 10v10h14V10',
    wallet: 'M3 7h15a3 3 0 013 3v8a2 2 0 01-2 2H5a2 2 0 01-2-2V7zm0 0V6a2 2 0 012-2h11M16 14h2',
    swap: 'M7 7h13l-3-3M17 17H4l3 3',
    loan: 'M3 21h18M5 21V10M9 21V10M15 21V10M19 21V10M2 10l10-6 10 6',
    card: 'M2 6h20v12H2zM2 10h20M6 15h4',
    lock: 'M6 11h12v9H6zM8 11V8a4 4 0 018 0v3',
    up: 'M7 17L17 7M8 7h9v9',
    down: 'M17 7L7 17M16 17H7V8',
    plus: 'M12 5v14M5 12h14',
    copy: 'M9 9h11v11H9zM5 15V4h11',
    globe: 'M12 3a9 9 0 100 18 9 9 0 000-18zM3 12h18M12 3c3 3 3 15 0 18M12 3c-3 3-3 15 0 18',
    wifi: 'M5 12a10 10 0 0114 0M8 15a6 6 0 018 0M11.5 18h1',
    shield: 'M12 3l8 3v6c0 5-3.5 8-8 9-4.5-1-8-4-8-9V6z',
    check: 'M5 12l5 5L20 7',
} as const;

export type IconName = keyof typeof PATHS;

export function Icon({ name, size = 20 }: Readonly<{ name: IconName; size?: number }>) {
    return (
        <svg
            width={size}
            height={size}
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.7"
            strokeLinecap="round"
            strokeLinejoin="round"
            aria-hidden="true"
        >
            <path d={PATHS[name]} />
        </svg>
    );
}
