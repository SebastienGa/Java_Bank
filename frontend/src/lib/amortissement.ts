export interface PointProjection {
    date: Date;
    restantCentimes: number;
}

export interface Projection {
    points: PointProjection[];
    interetsCentimes: number;
    fin: Date;
}

const HORIZON_MAX_MOIS = 600;

export function projeterCapitalRestant(
    restantCentimes: number,
    mensualiteCentimes: number,
    tauxPourMille: number,
    depart: Date = new Date(),
): Projection | null {
    const tauxMensuel = tauxPourMille / 1000 / 12;
    if (restantCentimes <= 0 || mensualiteCentimes <= restantCentimes * tauxMensuel) return null;

    const moisDepuisDepart = (mois: number) => new Date(depart.getFullYear(), depart.getMonth() + mois, 1);
    const points: PointProjection[] = [{ date: moisDepuisDepart(0), restantCentimes }];
    let restant = restantCentimes;
    let totalPaye = 0;

    for (let mois = 1; restant > 0 && mois <= HORIZON_MAX_MOIS; mois++) {
        const du = restant * (1 + tauxMensuel);
        const echeance = Math.min(mensualiteCentimes, du);
        totalPaye += echeance;
        restant = du - echeance;
        points.push({ date: moisDepuisDepart(mois), restantCentimes: Math.round(restant) });
    }

    return {
        points,
        interetsCentimes: Math.round(totalPaye - restantCentimes),
        fin: points[points.length - 1].date,
    };
}
