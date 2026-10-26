-- PI Saúde Recife | SCRUM-50 | Regra RF11
-- Complementa a restrição de exclusão já prevista em agenda.sql.
-- Execute primeiro em desenvolvimento e confirme o schema da base de destino.
-- Este script pressupõe public.agendamento com id_profissional, inicio e status.

BEGIN;

-- Pré-validação: se retornar linhas, resolva os duplicados antes de criar o índice.
SELECT id_profissional, inicio, COUNT(*) AS quantidade
FROM public.agendamento
WHERE status <> 'CANCELADO'
GROUP BY id_profissional, inicio
HAVING COUNT(*) > 1;

-- Índice único parcial exigido pela SCRUM-50: não permite dois agendamentos
-- não cancelados com o mesmo instante inicial para o mesmo profissional.
-- A restrição EXCLUDE (se instalada por agenda.sql) continua sendo necessária
-- para impedir sobreposição de períodos mesmo quando os inícios são diferentes.
CREATE UNIQUE INDEX IF NOT EXISTS uq_agendamento_horario
    ON public.agendamento (id_profissional, inicio)
    WHERE status <> 'CANCELADO';

COMMIT;

-- Validação pós-implantação: deve retornar a definição do índice.
SELECT indexname, indexdef
FROM pg_indexes
WHERE schemaname = 'public'
  AND tablename = 'agendamento'
  AND indexname = 'uq_agendamento_horario';
