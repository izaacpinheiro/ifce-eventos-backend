CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE inscricao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data DATE NOT NULL DEFAULT CURRENT_DATE,
    status BOOLEAN DEFAULT true,
    id_evento UUID,
    FOREIGN KEy (id_evento) REFERENCES evento(id)
);