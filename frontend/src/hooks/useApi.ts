import { useEffect, useState } from 'react';

interface ApiState<T> {
    data: T | null;
    error: string | null;
}

export function useApi<T>(path: string, refreshKey = 0) {
    const [state, setState] = useState<ApiState<T>>({ data: null, error: null });

    useEffect(() => {
        const controller = new AbortController();
        fetch(`${import.meta.env.VITE_API_URL}${path}`, { signal: controller.signal })
            .then((response) => {
                if (!response.ok) throw new Error(`Erreur HTTP ${response.status}`);
                return response.json() as Promise<T>;
            })
            .then((data) => setState({ data, error: null }))
            .catch((err: Error) => {
                if (err.name !== 'AbortError') setState((previous) => ({ data: previous.data, error: err.message }));
            });
        return () => controller.abort();
    }, [path, refreshKey]);

    return { ...state, loading: state.data === null && state.error === null };
}
