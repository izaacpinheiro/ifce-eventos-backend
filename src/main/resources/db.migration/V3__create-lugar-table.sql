CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE lugar (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(45) NOT NULL,
    tipo VARCHAR(20),
    capacidade INT,
    localizacao VARCHAR(90)
);