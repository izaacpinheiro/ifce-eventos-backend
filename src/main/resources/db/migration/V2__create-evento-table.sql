CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE evento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(90) NOT NULL,
    descricao VARCHAR(250) NOT NULL,
    status_aprovacao VARCHAR(20) NOT NULL,
    remote BOOLEAN NOT NULL,
    id_criador UUID,
    FOREIGN KEY (id_criador) REFERENCES usuario(id)
);