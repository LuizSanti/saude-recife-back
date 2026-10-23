-- SCRUM 15 - migration para banco já existente.

CREATE TABLE IF NOT EXISTS public.disponibilidade (
      id_disponibilidade BIGSERIAL PRIMARY KEY,

      id_profissional BIGINT NOT NULL,
      id_clinica BIGINT NOT NULL,
      id_especialidade BIGINT NOT NULL,

      data DATE NOT NULL,
      hora_inicio TIME NOT NULL,
      hora_fim TIME NOT NULL,

      ativo BOOLEAN DEFAULT TRUE NOT NULL,

      CONSTRAINT disponibilidade_horario_valido
      CHECK (hora_inicio < hora_fim),

    CONSTRAINT disponibilidade_id_profissional_fkey
    FOREIGN KEY (id_profissional)
    REFERENCES public.profissional(id_profissional),

    CONSTRAINT disponibilidade_id_clinica_fkey
    FOREIGN KEY (id_clinica)
    REFERENCES public.clinica(id_clinica),

    CONSTRAINT disponibilidade_id_especialidade_fkey
    FOREIGN KEY (id_especialidade)
    REFERENCES public.especialidade(id_especialidade)
    );

CREATE INDEX IF NOT EXISTS idx_disponibilidade_consulta
    ON public.disponibilidade (
    data,
    id_clinica,
    id_especialidade,
    id_profissional
    );