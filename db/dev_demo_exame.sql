BEGIN;

DO $$
DECLARE
    data_demo DATE := CURRENT_DATE + (8 - EXTRACT(ISODOW FROM CURRENT_DATE)::INTEGER);
    exame_id BIGINT;
    disponibilidade_id BIGINT;
BEGIN
    SELECT id_exame
    INTO exame_id
    FROM public.exame
    WHERE nome = 'Hemograma Completo'
      AND ativo;

    IF exame_id IS NULL OR NOT EXISTS (
        SELECT 1
        FROM public.clinica
        WHERE id_clinica = 3
          AND ativo
    ) THEN
        RAISE EXCEPTION 'O exame ou a unidade de demonstracao nao esta ativo.';
    END IF;

    INSERT INTO public.disponibilidade_exame (
        id_exame,
        id_clinica,
        data,
        hora_inicio,
        hora_fim,
        ativo
    )
    SELECT exame_id, 3, data_demo, TIME '10:00', TIME '10:30', TRUE
    WHERE NOT EXISTS (
        SELECT 1
        FROM public.disponibilidade_exame d
        WHERE d.id_exame = exame_id
          AND d.id_clinica = 3
          AND d.data = data_demo
          AND d.ativo
          AND d.hora_inicio < TIME '10:30'
          AND d.hora_fim > TIME '10:00'
    )
      AND NOT EXISTS (
        SELECT 1
        FROM public.agendamento_exame a
        WHERE a.id_exame = exame_id
          AND a.id_clinica = 3
          AND a.status = 'CONFIRMADO'
          AND a.inicio < ((data_demo + TIME '10:30') AT TIME ZONE 'America/Recife')
          AND a.fim > ((data_demo + TIME '10:00') AT TIME ZONE 'America/Recife')
    )
    RETURNING id_disponibilidade_exame INTO disponibilidade_id;

    IF disponibilidade_id IS NULL THEN
        RAISE EXCEPTION 'Nao foi possivel inserir o horario de demonstracao em %.', data_demo;
    END IF;

    RAISE NOTICE 'Horario para Hemograma Completo criado: id %, data %, 10:00-10:30.',
        disponibilidade_id, data_demo;
END $$;

GRANT SELECT ON public.exame, public.disponibilidade_exame TO saude_user;
GRANT SELECT, INSERT, UPDATE ON public.agendamento_exame TO saude_user;
GRANT USAGE, SELECT ON SEQUENCE public.agendamento_exame_id_agendamento_exame_seq TO saude_user;

COMMIT;
