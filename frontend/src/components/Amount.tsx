import { useCountUp } from '../hooks/useCountUp';
import { formatCentimes } from '../lib/format';

interface AmountProps {
    centimes: number;
    className?: string;
}

export function Amount({ centimes, className }: Readonly<AmountProps>) {
    const animated = useCountUp(centimes);
    return (
        <span className={className ? `amount ${className}` : 'amount'} aria-label={formatCentimes(centimes)}>
            {formatCentimes(Math.round(animated))}
        </span>
    );
}
