CREATE TABLE usuario (
     id_usuario BIGSERIAL PRIMARY KEY,
     nome VARCHAR(150) NOT NULL,
     email VARCHAR(150) UNIQUE NOT NULL,
     senha_hash VARCHAR(255) NOT NULL,
     telefone VARCHAR(20),
     tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('PACIENTE', 'PROFISSIONAL', 'ADMIN')),
     ativo BOOLEAN NOT NULL DEFAULT TRUE,
     criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE paciente (
    id_paciente BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo VARCHAR(30),
    observacoes TEXT,
    id_usuario BIGINT UNIQUE NOT NULL REFERENCES usuario(id_usuario)
);

CREATE TABLE profissional (
    id_profissional BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    conselho VARCHAR(20) NOT NULL,
    registro_profissional VARCHAR(20) NOT NULL,
    uf_registro CHAR(2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    id_usuario BIGINT UNIQUE NOT NULL REFERENCES usuario(id_usuario),
    CONSTRAINT uq_registro_conselho_uf UNIQUE (registro_profissional, conselho, uf_registro)
);

CREATE TABLE especialidade (
    id_especialidade BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE clinica (
     id_clinica BIGSERIAL PRIMARY KEY,
     nome VARCHAR(150) NOT NULL,
     cnpj VARCHAR(18) UNIQUE NOT NULL,
     telefone VARCHAR(20),
     email VARCHAR(150),
     logradouro VARCHAR(180),
     numero VARCHAR(20),
     bairro VARCHAR(100),
     cidade VARCHAR(100),
     uf CHAR(2),
     ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE profissional_clinica (
    id_profissional_clinica BIGSERIAL PRIMARY KEY,
    id_profissional BIGINT REFERENCES profissional(id_profissional),
    id_clinica BIGINT REFERENCES clinica(id_clinica),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_profissional_clinica UNIQUE (id_profissional, id_clinica)
);

CREATE TABLE profissional_especialidade(
    id_profissional BIGINT REFERENCES profissional(id_profissional),
    id_especialidade BIGINT REFERENCES especialidade(id_especialidade),
    PRIMARY KEY (id_profissional, id_especialidade)
);

CREATE TABLE clinica_especialidade(
    id_clinica BIGINT REFERENCES clinica(id_clinica),
    id_especialidade BIGINT REFERENCES especialidade(id_especialidade),
    PRIMARY KEY (id_clinica, id_especialidade)
);