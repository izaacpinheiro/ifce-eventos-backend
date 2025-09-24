CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE evento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(90) NOT NULL,
    descricao TEXT,
    status_aprovacao BOOLEAN DEFAULT false,
    remote BOOLEAN NOT NULL,
    id_criador UUID,
    FOREIGN KEY (id_criador) REFERENCES usuario(id)
);