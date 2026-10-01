CREATE TABLE transactions (
    id                     UUID         PRIMARY KEY,
    account_id             UUID         NOT NULL REFERENCES accounts (id),
    counterpart_account_id UUID         REFERENCES accounts (id),
    libelle                VARCHAR(255) NOT NULL,
    categorie              VARCHAR(50)  NOT NULL,
    montant_centimes       BIGINT       NOT NULL,
    date_operation         TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_transactions_account_date ON transactions (account_id, date_operation DESC);
CREATE INDEX idx_transactions_date ON transactions (date_operation DESC);
