CREATE TABLE clients (
    id     UUID PRIMARY KEY,
    prenom VARCHAR(255) NOT NULL,
    nom    VARCHAR(255) NOT NULL,
    email  VARCHAR(255) NOT NULL
);

INSERT INTO clients (id, prenom, nom, email) VALUES
    ('a0000000-0000-0000-0000-000000000001', 'Alice', 'Martin', 'alice.martin@example.com'),
    ('a0000000-0000-0000-0000-000000000002', 'Bob', 'Durand', 'bob.durand@example.com'),
    ('a0000000-0000-0000-0000-000000000003', 'Chloé', 'Bernard', 'chloe.bernard@example.com');

ALTER TABLE accounts ADD COLUMN client_id UUID;

UPDATE accounts SET client_id = 'a0000000-0000-0000-0000-000000000001' WHERE id = '11111111-1111-1111-1111-111111111111';
UPDATE accounts SET client_id = 'a0000000-0000-0000-0000-000000000002' WHERE id = '22222222-2222-2222-2222-222222222222';
UPDATE accounts SET client_id = 'a0000000-0000-0000-0000-000000000003' WHERE id = '33333333-3333-3333-3333-333333333333';

ALTER TABLE accounts ALTER COLUMN client_id SET NOT NULL;
ALTER TABLE accounts ADD CONSTRAINT fk_accounts_client FOREIGN KEY (client_id) REFERENCES clients (id);
ALTER TABLE accounts DROP COLUMN titulaire;

CREATE TABLE loans (
    id                        UUID PRIMARY KEY,
    client_id                 UUID   NOT NULL REFERENCES clients (id),
    montant_initial_centimes  BIGINT NOT NULL,
    montant_restant_centimes  BIGINT NOT NULL,
    taux_interet_pour_mille   INT    NOT NULL,
    mensualite_centimes       BIGINT NOT NULL,
    date_debut                DATE   NOT NULL
);

INSERT INTO loans (id, client_id, montant_initial_centimes, montant_restant_centimes, taux_interet_pour_mille, mensualite_centimes, date_debut) VALUES
    ('b0000000-0000-0000-0000-000000000001', 'a0000000-0000-0000-0000-000000000001', 18000000, 6300000, 35, 105000, '2016-06-01'),
    ('b0000000-0000-0000-0000-000000000002', 'a0000000-0000-0000-0000-000000000002', 1500000, 1200000, 49, 28000, '2024-09-10');
