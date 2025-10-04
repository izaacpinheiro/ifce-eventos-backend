CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE inscricao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(20) NOT NULL,
    id_evento UUID,
    FOREIGN KEY (id_evento) REFERENCES evento(id),
    id_usuario UUID,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);