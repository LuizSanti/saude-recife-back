-- PI Saúde Recife | consultas de validação após aplicar db/agenda.sql
-- Estas consultas são somente leitura; não criam nem alteram dados.

-- 1. Confirmar que as cinco tabelas existem.
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN (
      'grade_atendimento',
      'indisponibilidade_profissional',
      'excecao_agenda',
      'agendamento',
      'configuracao_agendamento'
  )
ORDER BY table_name;

-- 2. Ver estrutura e restrições da tabela de agendamentos.
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_schema = 'public' AND table_name = 'agendamento'
ORDER BY ordinal_position;

SELECT conname, contype
FROM pg_constraint
WHERE conrelid = 'public.agendamento'::regclass
ORDER BY contype, conname;

-- 3. Agendamentos confirmados que se sobreporiam por profissional.
-- Esperado: zero linhas. A restrição ex_agendamento_profissional_periodo
-- também impede que novas sobreposições sejam persistidas.
SELECT a.id_agendamento AS agendamento_a,
       b.id_agendamento AS agendamento_b,
       a.id_profissional,
       a.inicio AS inicio_a, a.fim AS fim_a,
       b.inicio AS inicio_b, b.fim AS fim_b
FROM public.agendamento a
JOIN public.agendamento b
  ON a.id_profissional = b.id_profissional
 AND a.id_agendamento < b.id_agendamento
 AND tstzrange(a.inicio, a.fim, '[)') && tstzrange(b.inicio, b.fim, '[)')
WHERE a.status = 'CONFIRMADO'
  AND b.status = 'CONFIRMADO';

-- 4. Grades ativas que se sobrepõem para o mesmo profissional e dia.
-- Esperado: zero linhas.
SELECT a.id_grade AS grade_a,
       b.id_grade AS grade_b,
       a.id_profissional,
       a.dia_semana,
       a.hora_inicio AS inicio_a, a.hora_fim AS fim_a,
       b.hora_inicio AS inicio_b, b.hora_fim AS fim_b
FROM public.grade_atendimento a
JOIN public.grade_atendimento b
  ON a.id_profissional = b.id_profissional
 AND a.dia_semana = b.dia_semana
 AND a.id_grade < b.id_grade
 AND a.ativo AND b.ativo
 AND a.hora_inicio < b.hora_fim
 AND b.hora_inicio < a.hora_fim
 AND daterange(a.vigencia_inicio,
               CASE WHEN a.vigencia_fim IS NULL THEN NULL ELSE a.vigencia_fim + 1 END,
               '[)')
     &&
     daterange(b.vigencia_inicio,
               CASE WHEN b.vigencia_fim IS NULL THEN NULL ELSE b.vigencia_fim + 1 END,
               '[)');

-- 5. Confirmar que cancelamentos possuem data de cancelamento e confirmados não.
-- Esperado: zero linhas.
SELECT id_agendamento, status, cancelado_em
FROM public.agendamento
WHERE (status = 'CONFIRMADO' AND cancelado_em IS NOT NULL)
   OR (status = 'CANCELADO' AND cancelado_em IS NULL);

-- 6. Verificar se a antecedência máxima foi configurada.
-- Zero linhas significa que o projeto ainda precisa definir/inserir o valor.
SELECT antecedencia_maxima_dias, atualizado_em
FROM public.configuracao_agendamento
WHERE id_configuracao = 1;

-- 7. Verificar as associações cadastradas antes de criar grades/agendamentos.
SELECT pc.id_profissional, pc.id_clinica
FROM public.profissional_clinica pc
WHERE pc.id_profissional IS NOT NULL AND pc.id_clinica IS NOT NULL
ORDER BY pc.id_profissional, pc.id_clinica;

SELECT pe.id_profissional, pe.id_especialidade
FROM public.profissional_especialidade pe
ORDER BY pe.id_profissional, pe.id_especialidade;
