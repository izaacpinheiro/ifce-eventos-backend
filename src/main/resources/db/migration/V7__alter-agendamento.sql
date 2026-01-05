-- evento e lugar são obrigatórios
ALTER TABLE agendamento
    ALTER COLUMN id_evento SET NOT NULL,
    ALTER COLUMN id_lugar SET NOT NULL;

-- garantir que a hora final seja maior que a inicial
ALTER TABLE agendamento
    ADD CONSTRAINT chk_horario_valido
    CHECK (hora_fim > hora_inicio);
