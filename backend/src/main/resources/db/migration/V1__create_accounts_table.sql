CREATE TABLE accounts (
    id             UUID PRIMARY KEY,
    titulaire      VARCHAR(255) NOT NULL,
    solde_centimes BIGINT       NOT NULL DEFAULT 0
);

INSERT INTO accounts (id, titulaire, solde_centimes) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Alice Martin', 150000),
    ('22222222-2222-2222-2222-222222222222', 'Bob Durand', 25000),
    ('33333333-3333-3333-3333-333333333333', 'Chloé Bernard', 0);
