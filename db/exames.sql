BEGIN;

CREATE TABLE IF NOT EXISTS public.exame (
    id_exame BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO public.exame (nome, ativo) VALUES
    ('Antígeno Prostático Específico', TRUE),
    ('Avaliação Cardiológica', TRUE),
    ('Avaliação Oftalmológica', TRUE),
    ('Colonoscopia', TRUE),
    ('Glicemia de Jejum/Hemoglobina', TRUE),
    ('Hemograma Completo', TRUE),
    ('Mamografia', TRUE)
ON CONFLICT (nome) DO UPDATE
SET ativo = EXCLUDED.ativo;

CREATE TABLE IF NOT EXISTS public.disponibilidade_exame (
    id_disponibilidade_exame BIGSERIAL PRIMARY KEY,
    id_exame BIGINT NOT NULL REFERENCES public.exame(id_exame),
    id_clinica BIGINT NOT NULL REFERENCES public.clinica(id_clinica),
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT disponibilidade_exame_horario_valido
        CHECK (hora_inicio < hora_fim)
);

CREATE INDEX IF NOT EXISTS idx_disponibilidade_exame_consulta
    ON public.disponibilidade_exame (data, id_clinica, id_exame);

CREATE TABLE IF NOT EXISTS public.agendamento_exame (
    id_agendamento_exame BIGSERIAL PRIMARY KEY,
    id_paciente BIGINT NOT NULL REFERENCES public.paciente(id_paciente),
    id_disponibilidade_exame BIGINT NOT NULL
        REFERENCES public.disponibilidade_exame(id_disponibilidade_exame),
    id_exame BIGINT NOT NULL REFERENCES public.exame(id_exame),
    id_clinica BIGINT NOT NULL REFERENCES public.clinica(id_clinica),
    inicio TIMESTAMPTZ NOT NULL,
    fim TIMESTAMPTZ NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMADO'
        CHECK (status IN ('CONFIRMADO', 'CANCELADO')),
    codigo_confirmacao VARCHAR(12) NOT NULL UNIQUE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelado_em TIMESTAMPTZ,
    CONSTRAINT agendamento_exame_periodo_valido CHECK (fim > inicio)
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_agendamento_exame_horario_confirmado
    ON public.agendamento_exame (id_disponibilidade_exame)
    WHERE status = 'CONFIRMADO';

CREATE INDEX IF NOT EXISTS idx_agendamento_exame_paciente_inicio
    ON public.agendamento_exame (id_paciente, inicio DESC);

GRANT SELECT ON public.exame, public.disponibilidade_exame TO saude_user;
GRANT SELECT, INSERT, UPDATE ON public.agendamento_exame TO saude_user;
GRANT USAGE, SELECT ON SEQUENCE public.agendamento_exame_id_agendamento_exame_seq
    TO saude_user;

COMMIT;
