-- Esquema reconstruído do backup PostgreSQL local.
-- Revise antes de aplicar em qualquer banco.
-- Não contém dados, proprietários ou permissões do ambiente original.

CREATE TABLE public.clinica (
    id_clinica bigint NOT NULL,
    nome character varying(150) NOT NULL,
    cnpj character varying(18),
    telefone character varying(20),
    email character varying(150),
    logradouro character varying(180),
    numero character varying(20),
    bairro character varying(100),
    cidade character varying(100),
    uf character(2),
    ativo boolean DEFAULT true NOT NULL,
    cnes text,
    distrito integer,
    latitude double precision,
    longitude double precision,
    flag_cnes_placeholder boolean DEFAULT false NOT NULL,
    flag_cnpj_ausente boolean DEFAULT false NOT NULL,
    flag_horario_demonstrativo boolean DEFAULT false NOT NULL,
    fonte_dados text,
    data_referencia date,
    importado_em timestamp with time zone,
    tipo_servico text,
    servico text,
    cd_bairro text,
    nm_bairro_censo text,
    status_bairro text,
    tipo_correcao_bairro text,
    horario text
);

CREATE TABLE public.clinica_especialidade (
    id_clinica bigint NOT NULL,
    id_especialidade bigint NOT NULL
);

CREATE SEQUENCE public.clinica_id_clinica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.clinica_id_clinica_seq OWNED BY public.clinica.id_clinica;

CREATE TABLE public.especialidade (
    id_especialidade bigint NOT NULL,
    nome character varying(100) NOT NULL,
    descricao text,
    ativo boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE public.especialidade_id_especialidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.especialidade_id_especialidade_seq OWNED BY public.especialidade.id_especialidade;

CREATE TABLE public.paciente (
    id_paciente bigint NOT NULL,
    cpf character varying(14) NOT NULL,
    data_nascimento date NOT NULL,
    sexo character varying(30),
    observacoes text,
    id_usuario bigint NOT NULL
);

CREATE SEQUENCE public.paciente_id_paciente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.paciente_id_paciente_seq OWNED BY public.paciente.id_paciente;

CREATE TABLE public.profissional (
    id_profissional bigint NOT NULL,
    cpf character varying(14) NOT NULL,
    conselho character varying(20) NOT NULL,
    registro_profissional character varying(20) NOT NULL,
    uf_registro character(2) NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    id_usuario bigint NOT NULL
);

CREATE TABLE public.profissional_clinica (
    id_profissional_clinica bigint NOT NULL,
    id_profissional bigint,
    id_clinica bigint,
    ativo boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE public.profissional_clinica_id_profissional_clinica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.profissional_clinica_id_profissional_clinica_seq OWNED BY public.profissional_clinica.id_profissional_clinica;

CREATE TABLE public.profissional_especialidade (
    id_profissional bigint NOT NULL,
    id_especialidade bigint NOT NULL
);

CREATE SEQUENCE public.profissional_id_profissional_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.profissional_id_profissional_seq OWNED BY public.profissional.id_profissional;

CREATE TABLE public.usuario (
    id_usuario bigint NOT NULL,
    nome character varying(150) NOT NULL,
    email character varying(150) NOT NULL,
    senha_hash character varying(255) NOT NULL,
    telefone character varying(20),
    tipo_usuario character varying(20) NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    criado_em timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT usuario_tipo_usuario_check CHECK (((tipo_usuario)::text = ANY ((ARRAY['PACIENTE'::character varying, 'PROFISSIONAL'::character varying, 'ADMIN'::character varying])::text[])))
);

CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;

ALTER TABLE ONLY public.clinica ALTER COLUMN id_clinica SET DEFAULT nextval('public.clinica_id_clinica_seq'::regclass);

ALTER TABLE ONLY public.especialidade ALTER COLUMN id_especialidade SET DEFAULT nextval('public.especialidade_id_especialidade_seq'::regclass);

ALTER TABLE ONLY public.paciente ALTER COLUMN id_paciente SET DEFAULT nextval('public.paciente_id_paciente_seq'::regclass);

ALTER TABLE ONLY public.profissional ALTER COLUMN id_profissional SET DEFAULT nextval('public.profissional_id_profissional_seq'::regclass);

ALTER TABLE ONLY public.profissional_clinica ALTER COLUMN id_profissional_clinica SET DEFAULT nextval('public.profissional_clinica_id_profissional_clinica_seq'::regclass);

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);

ALTER TABLE ONLY public.clinica
    ADD CONSTRAINT clinica_cnpj_key UNIQUE (cnpj);

ALTER TABLE ONLY public.clinica_especialidade
    ADD CONSTRAINT clinica_especialidade_pkey PRIMARY KEY (id_clinica, id_especialidade);

ALTER TABLE ONLY public.clinica
    ADD CONSTRAINT clinica_pkey PRIMARY KEY (id_clinica);

ALTER TABLE ONLY public.especialidade
    ADD CONSTRAINT especialidade_pkey PRIMARY KEY (id_especialidade);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_cpf_key UNIQUE (cpf);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_id_usuario_key UNIQUE (id_usuario);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (id_paciente);

ALTER TABLE ONLY public.profissional_clinica
    ADD CONSTRAINT profissional_clinica_pkey PRIMARY KEY (id_profissional_clinica);

ALTER TABLE ONLY public.profissional
    ADD CONSTRAINT profissional_cpf_key UNIQUE (cpf);

ALTER TABLE ONLY public.profissional_especialidade
    ADD CONSTRAINT profissional_especialidade_pkey PRIMARY KEY (id_profissional, id_especialidade);

ALTER TABLE ONLY public.profissional
    ADD CONSTRAINT profissional_id_usuario_key UNIQUE (id_usuario);

ALTER TABLE ONLY public.profissional
    ADD CONSTRAINT profissional_pkey PRIMARY KEY (id_profissional);

ALTER TABLE ONLY public.profissional_clinica
    ADD CONSTRAINT uq_profissional_clinica UNIQUE (id_profissional, id_clinica);

ALTER TABLE ONLY public.profissional
    ADD CONSTRAINT uq_registro_conselho_uf UNIQUE (registro_profissional, conselho, uf_registro);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);

CREATE UNIQUE INDEX uq_clinica_cnes ON public.clinica USING btree (cnes) WHERE (cnes IS NOT NULL);

ALTER TABLE ONLY public.clinica_especialidade
    ADD CONSTRAINT clinica_especialidade_id_clinica_fkey FOREIGN KEY (id_clinica) REFERENCES public.clinica(id_clinica);

ALTER TABLE ONLY public.clinica_especialidade
    ADD CONSTRAINT clinica_especialidade_id_especialidade_fkey FOREIGN KEY (id_especialidade) REFERENCES public.especialidade(id_especialidade);

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);

ALTER TABLE ONLY public.profissional_clinica
    ADD CONSTRAINT profissional_clinica_id_clinica_fkey FOREIGN KEY (id_clinica) REFERENCES public.clinica(id_clinica);

ALTER TABLE ONLY public.profissional_clinica
    ADD CONSTRAINT profissional_clinica_id_profissional_fkey FOREIGN KEY (id_profissional) REFERENCES public.profissional(id_profissional);

ALTER TABLE ONLY public.profissional_especialidade
    ADD CONSTRAINT profissional_especialidade_id_especialidade_fkey FOREIGN KEY (id_especialidade) REFERENCES public.especialidade(id_especialidade);

ALTER TABLE ONLY public.profissional_especialidade
    ADD CONSTRAINT profissional_especialidade_id_profissional_fkey FOREIGN KEY (id_profissional) REFERENCES public.profissional(id_profissional);

ALTER TABLE ONLY public.profissional
    ADD CONSTRAINT profissional_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


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
