-- Habilita o status REALIZADO para consultas concluídas por profissionais.
-- Execute manualmente após revisar e validar em um ambiente de desenvolvimento.

BEGIN;

ALTER TABLE public.agendamento
    DROP CONSTRAINT IF EXISTS agendamento_status_check,
    DROP CONSTRAINT IF EXISTS ck_agendamento_status,
    DROP CONSTRAINT IF EXISTS ck_agendamento_cancelamento;

ALTER TABLE public.agendamento
    ADD CONSTRAINT ck_agendamento_status
        CHECK (status IN ('CONFIRMADO', 'CANCELADO', 'REALIZADO')),
    ADD CONSTRAINT ck_agendamento_cancelamento
        CHECK (
            (status = 'CONFIRMADO' AND cancelado_em IS NULL)
            OR (status = 'CANCELADO' AND cancelado_em IS NOT NULL)
            OR (status = 'REALIZADO' AND cancelado_em IS NULL)
        );

COMMIT;
