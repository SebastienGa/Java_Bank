export interface Account {
    id: string;
    clientPrenom: string;
    clientNom: string;
    soldeCentimes: number;
}

export interface Loan {
    id: string;
    clientPrenom: string;
    clientNom: string;
    montantInitialCentimes: number;
    montantRestantCentimes: number;
    tauxInteretPourMille: number;
    mensualiteCentimes: number;
    dateDebut: string;
    progression: number;
}

export interface TransferRequest {
    destinationAccountId: string;
    montantCentimes: number;
}

export interface ApiError {
    message: string;
}