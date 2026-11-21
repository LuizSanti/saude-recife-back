BEGIN;

DO $$
DECLARE
    data_demo DATE := CURRENT_DATE + (8 - EXTRACT(ISODOW FROM CURRENT_DATE)::INTEGER);
    disponibilidade_id BIGINT;
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM public.profissional p
        JOIN public.profissional_clinica pc
            ON pc.id_profissional = p.id_profissional
        JOIN public.profissional_especialidade pe
            ON pe.id_profissional = p.id_profissional
        JOIN public.clinica_especialidade ce
            ON ce.id_clinica = pc.id_clinica
            AND ce.id_especialidade = pe.id_especialidade
        JOIN public.clinica c
            ON c.id_clinica = pc.id_clinica
        JOIN public.especialidade e
            ON e.id_especialidade = pe.id_especialidade
        WHERE p.id_profissional = 1
          AND pc.id_clinica = 1
          AND pe.id_especialidade = 11
          AND p.ativo
          AND pc.ativo
          AND c.ativo
          AND e.ativo
    ) THEN
        RAISE EXCEPTION 'A combinacao de demonstracao profissional/clinica/especialidade nao esta ativa.';
    END IF;

    INSERT INTO public.disponibilidade (
        id_profissional,
        id_clinica,
        id_especialidade,
        data,
        hora_inicio,
        hora_fim,
        ativo
    )
    SELECT 1, 1, 11, data_demo, TIME '09:00', TIME '09:30', TRUE
    WHERE NOT EXISTS (
        SELECT 1
        FROM public.disponibilidade d
        WHERE d.id_profissional = 1
          AND d.data = data_demo
          AND d.ativo
          AND d.hora_inicio < TIME '09:30'
          AND d.hora_fim > TIME '09:00'
    )
      AND NOT EXISTS (
        SELECT 1
        FROM public.agendamento a
        WHERE a.id_profissional = 1
          AND a.status = 'CONFIRMADO'
          AND a.inicio < ((data_demo + TIME '09:30') AT TIME ZONE 'America/Recife')
          AND a.fim > ((data_demo + TIME '09:00') AT TIME ZONE 'America/Recife')
    )
    RETURNING id_disponibilidade INTO disponibilidade_id;

    IF disponibilidade_id IS NULL THEN
        RAISE EXCEPTION 'Nao foi possivel inserir o horario; confira sobreposicoes em %.', data_demo;
    END IF;

    RAISE NOTICE 'Disponibilidade de demonstracao criada: id %, data %, 09:00-09:30.',
        disponibilidade_id, data_demo;
END $$;

COMMIT;
