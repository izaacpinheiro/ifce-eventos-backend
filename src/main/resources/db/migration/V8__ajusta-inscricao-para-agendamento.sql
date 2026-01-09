
ALTER TABLE inscricao DROP COLUMN id_evento;

ALTER TABLE inscricao ADD COLUMN id_agendamento UUID NOT NULL;

ALTER TABLE inscricao
ADD CONSTRAINT fk_inscricao_agendamento
FOREIGN KEY (id_agendamento)
REFERENCES agendamento(id);

ALTER TABLE inscricao
DROP CONSTRAINT IF EXISTS inscricao_id_usuario_fkey;

ALTER TABLE inscricao
ADD CONSTRAINT fk_inscricao_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id);
