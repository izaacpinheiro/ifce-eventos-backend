CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE agendamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    id_evento UUID,
    FOREIGN KEY (id_evento) REFERENCES evento(id),
    id_lugar UUID,
    FOREIGN KEY (id_local) REFERENCES lugar(id)
);