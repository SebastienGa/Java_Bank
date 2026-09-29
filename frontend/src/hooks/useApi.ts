import { useEffect, useState } from 'react';

interface ApiState<T> {
    requestKey: string;
    data: T | null;
    error: string | null;
}

interface ApiResult<T> {
    data: T | null;
    loading: boolean;
    error: string | null;
    /** true quand la réponse tarde : l'hébergement gratuit (Render) met ~1 min à se réveiller. */
    waking: boolean;
}

const WAKING_DELAY_MS = 4000;

export function useApi<T>(path: string, reloadKey = 0): ApiResult<T> {
    const requestKey = `${path}#${reloadKey}`;
    const [state, setState] = useState<ApiState<T>>({ requestKey: '', data: null, error: null });
    const [wakingKey, setWakingKey] = useState('');

    useEffect(() => {
        const controller = new AbortController();
        const timer = setTimeout(() => setWakingKey(requestKey), WAKING_DELAY_MS);

        fetch(`${import.meta.env.VITE_API_URL}${path}`, { signal: controller.signal })
            .then((response) => {
                if (!response.ok) throw new Error(`Erreur HTTP ${response.status}`);
                return response.json() as Promise<T>;
            })
            .then((data) => setState({ requestKey, data, error: null }))
            .catch((err: Error) => {
                if (err.name === 'AbortError') return;
                setState({ requestKey, data: null, error: err.message });
            })
            .finally(() => clearTimeout(timer));

        return () => {
            controller.abort();
            clearTimeout(timer);
        };
    }, [path, reloadKey, requestKey]);

    const loading = state.requestKey !== requestKey;
    return {
        // Pendant un rechargement, on garde les anciennes données à l'écran (pas de flash).
        data: state.data,
        loading,
        error: loading ? null : state.error,
        waking: loading && wakingKey === requestKey,
    };
}
