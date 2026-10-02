const PATHS = {
    overview: 'M4 13h6V4H4zM14 20h6V11h-6zM4 20h6v-3H4zM14 7h6V4h-6z',
    transfer: 'M4 8h13l-3.5-3.5M20 16H7l3.5 3.5',
    loans: 'M3 10 12 4l9 6M5 10v8M9.5 10v8M14.5 10v8M19 10v8M3 20h18',
    arrow: 'M5 12h14M13 6l6 6-6 6',
};

export type IconName = keyof typeof PATHS;

export function Icon({ name }: Readonly<{ name: IconName }>) {
    return (
        <svg className="icon" viewBox="0 0 24 24" aria-hidden="true">
            <path d={PATHS[name]} />
        </svg>
    );
}
