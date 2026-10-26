-- Dados de referência do projeto Saúde Sênior
-- Gerado a partir do backup local; revise antes de executar.
BEGIN;

INSERT INTO public.clinica ("id_clinica", "nome", "cnpj", "telefone", "email", "logradouro", "numero", "bairro", "cidade", "uf", "ativo", "cnes", "distrito", "latitude", "longitude", "flag_cnes_placeholder", "flag_cnpj_ausente", "flag_horario_demonstrativo", "fonte_dados", "data_referencia", "importado_em", "tipo_servico", "servico", "cd_bairro", "nm_bairro_censo", "status_bairro", "tipo_correcao_bairro", "horario") VALUES ('1', 'us 223 centro de reabilit fisio antônio nogueira de amorim', NULL, NULL, NULL, 'rua sete de setembro, s/n - boa vista', NULL, 'boa vista', 'Recife', 'PE', 't', '0028983', '1', '-8.06011924411', '-34.8829791526', 'f', 't', 't', 'Databricks - projeto-integrador.silver.unidades_para_seed', '2026-10-19', '2026-10-19 22:42:30.504734-03', 'centro de reabilitacao', 'reab', '2611606003', 'Boa Vista', 'RESOLVIDO', NULL, 'segunda a sexta 08:00 às 17:00');
INSERT INTO public.clinica ("id_clinica", "nome", "cnpj", "telefone", "email", "logradouro", "numero", "bairro", "cidade", "uf", "ativo", "cnes", "distrito", "latitude", "longitude", "flag_cnes_placeholder", "flag_cnpj_ausente", "flag_horario_demonstrativo", "fonte_dados", "data_referencia", "importado_em", "tipo_servico", "servico", "cd_bairro", "nm_bairro_censo", "status_bairro", "tipo_correcao_bairro", "horario") VALUES ('2', 'us 321 central de alergologia', NULL, NULL, NULL, 'av joao de barros, 800 - santo amaro', NULL, 'santo amaro', 'Recife', 'PE', 't', '0000906', '1', '-8.04758612363', '-34.8894922895', 'f', 't', 't', 'Databricks - projeto-integrador.silver.unidades_para_seed', '2026-10-19', '2026-10-19 22:42:30.504734-03', 'central de alergologia', 'alergo', '2611606002', 'Santo Amaro', 'RESOLVIDO', NULL, 'segunda à sexta 7:00 às 17:00');
INSERT INTO public.clinica ("id_clinica", "nome", "cnpj", "telefone", "email", "logradouro", "numero", "bairro", "cidade", "uf", "ativo", "cnes", "distrito", "latitude", "longitude", "flag_cnes_placeholder", "flag_cnpj_ausente", "flag_horario_demonstrativo", "fonte_dados", "data_referencia", "importado_em", "tipo_servico", "servico", "cd_bairro", "nm_bairro_censo", "status_bairro", "tipo_correcao_bairro", "horario") VALUES ('3', 'us 443 unidade pública de atend especializado upae arruda', NULL, NULL, NULL, 'avenida professor jose dos anjos', NULL, 'ponto de parada', 'Recife', 'PE', 't', '7993412', '2', '-8.0282484258', '-34.8911091682', 'f', 't', 't', 'Databricks - projeto-integrador.silver.unidades_para_seed', '2026-10-19', '2026-10-19 22:42:30.504734-03', 'unidade publica de atendimento especializado', 'upa-e', '2611606022', 'Ponto de Parada', 'RESOLVIDO', NULL, 'segunda a sexta 07:00 às 17:00');
INSERT INTO public.clinica ("id_clinica", "nome", "cnpj", "telefone", "email", "logradouro", "numero", "bairro", "cidade", "uf", "ativo", "cnes", "distrito", "latitude", "longitude", "flag_cnes_placeholder", "flag_cnpj_ausente", "flag_horario_demonstrativo", "fonte_dados", "data_referencia", "importado_em", "tipo_servico", "servico", "cd_bairro", "nm_bairro_censo", "status_bairro", "tipo_correcao_bairro", "horario") VALUES ('4', 'núcleo de desenvolvimento infantil/lessa de andrade', NULL, NULL, NULL, 'estrada dos remedios, 2416 - madalena', NULL, 'madalena', 'Recife', 'PE', 't', '9999999', '4', '-8.05891382154', '-34.9070090424', 't', 't', 't', 'Databricks - projeto-integrador.silver.unidades_para_seed', '2026-10-19', '2026-10-19 22:42:30.504734-03', 'núcleo de desenvolvimento infantil', 'ndi', '2611606062', 'Madalena', 'RESOLVIDO', NULL, 'segunda a sexta 08:00 às 17:00');

INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('1', 'alergia dermatologica', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('2', 'alergia respiratoria', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('3', 'assistencia social', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('4', 'cardiologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('5', 'colonoscopia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('6', 'ecocardiograma', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('7', 'endocrinologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('8', 'endoscopia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('9', 'enfermagem (estomatologia)', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('10', 'ergometria', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('11', 'fisioterapia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('12', 'fisioterapia respiratória infantil', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('13', 'fonoaudiologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('14', 'imunologia para alergias', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('15', 'nefrologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('16', 'neurologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('17', 'nutricao', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('18', 'ortopedia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('19', 'pneumologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('20', 'psicologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('21', 'psicoterapia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('22', 'psiquiatria', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('23', 'radiologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('24', 'reumatologia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('25', 'terapia ocupacional', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('26', 'teste alergico inalante e contato', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('27', 'ultrassonografia', NULL, 't');
INSERT INTO public.especialidade ("id_especialidade", "nome", "descricao", "ativo") VALUES ('28', 'urologia', NULL, 't');

INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '4');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '23');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '16');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '22');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '21');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '3');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '7');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '9');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '28');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '2');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '12');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '8');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '26');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '6');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('1', '11');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '15');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '14');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '5');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '19');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('2', '1');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('1', '25');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '24');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '20');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '27');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '25');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '17');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '11');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('1', '13');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '18');
INSERT INTO public.clinica_especialidade ("id_clinica", "id_especialidade") VALUES ('3', '10');

-- Atualiza as sequências das tabelas com IDs explícitos.
SELECT setval(pg_get_serial_sequence('public.clinica', 'id_clinica'), (SELECT MAX(id_clinica) FROM public.clinica)) WHERE EXISTS (SELECT 1 FROM public.clinica);
SELECT setval(pg_get_serial_sequence('public.especialidade', 'id_especialidade'), (SELECT MAX(id_especialidade) FROM public.especialidade)) WHERE EXISTS (SELECT 1 FROM public.especialidade);

COMMIT;
