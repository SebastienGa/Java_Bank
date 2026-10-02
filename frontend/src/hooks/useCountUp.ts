import { useEffect, useRef, useState } from 'react';

const DUREE_MS = 900;

export function useCountUp(target: number): number {
    const [value, setValue] = useState(0);
    const currentRef = useRef(0);

    useEffect(() => {
        const from = currentRef.current;
        if (from === target) return;

        const reducedMotion = globalThis.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
        const duration = reducedMotion ? 0 : DUREE_MS;
        const start = performance.now();
        let frame = 0;

        const tick = (now: number) => {
            const t = duration === 0 ? 1 : Math.min((now - start) / duration, 1);
            const eased = 1 - Math.pow(1 - t, 4);
            currentRef.current = from + (target - from) * eased;
            setValue(currentRef.current);
            if (t < 1) frame = requestAnimationFrame(tick);
        };
        frame = requestAnimationFrame(tick);
        return () => cancelAnimationFrame(frame);
    }, [target]);

    return value;
}
