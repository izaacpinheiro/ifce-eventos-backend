CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE lugar (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(45) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    capacidade INT NOT NULL,
    localizacao VARCHAR(90) NOT NULL
);