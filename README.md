# Saúde na Palma da Mão — Backend

API REST do Projeto Integrador "Saúde na Palma da Mão" (também referenciado como "Saúde Recife" no material do professor), voltado à pré-triagem e agendamento de consultas, com foco inicial no público idoso (60+ anos).

## Sobre o projeto

Sistema de agendamento de consultas médicas com cadastro de clínicas, profissionais, especialidades e pacientes. A 1ª entrega cobre o MVP Web (PWA) + Backend + Banco de Dados. A 2ª entrega adiciona aplicativo React Native e pré-triagem inteligente com IA.

Repositório irmão: `saude-palma-frontend` (PWA).

## Stack

- **Linguagem:** Java
- **Framework:** Spring Boot
- **Banco de dados:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Build:** Maven

## Estrutura do projeto

```
src/
├── main/
│   ├── java/.../controller   # Endpoints REST
│   ├── java/.../service      # Regras de negócio
│   ├── java/.../repository   # Acesso a dados (Spring Data JPA)
│   ├── java/.../model        # Entidades JPA
│   └── resources/
│       └── application.properties
└── test/
    └── java/...              # Testes
```

## Configuração do banco de dados

O schema é versionado via scripts SQL (não usar `ddl-auto=create`/`update` em produção). Configure `spring.jpa.hibernate.ddl-auto=validate` — o Hibernate confere se as entidades batem com o schema real, sem alterá-lo sozinho.

1. Crie um banco PostgreSQL local (ex: `saude_recife`)
2. Rode os scripts de criação de tabelas (pasta `/database` ou migrations Flyway, conforme decidido pela equipe)
3. Configure a conexão em `application.properties` (ver `application-example.properties`)

## Como rodar localmente

### Pré-requisitos
- Java 17+ (confirmar versão exata com a equipe)
- Maven
- PostgreSQL rodando localmente ou acessível remotamente

### Passos

```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd saude-palma-backend

# Configurar variáveis de ambiente / application.properties
cp src/main/resources/application-example.properties src/main/resources/application.properties
# Edite com suas credenciais locais de banco

# Rodar
mvn spring-boot:run
```

A API sobe por padrão em `http://localhost:8080`.

## Variáveis de configuração necessárias

| Variável | Descrição |
|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexão com o PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco |

Nunca commitar credenciais reais — usar `application-example.properties` como referência e manter o arquivo real fora do controle de versão (ver `.gitignore`).

## Testes

```bash
mvn test
```

## Documentação da API

(A definir: recomenda-se Springdoc/Swagger para gerar documentação automática dos endpoints a partir das anotações do Spring — facilita a integração com o time de frontend.)

## Deploy

Ambiente de deploy: **Render**. Backend, API e banco de dados devem estar publicados em ambiente acessível para demonstração da 1ª entrega (14/10/2026).

## Fluxo de contribuição

Antes de abrir um Pull Request, leia o [`CONTRIBUTING.md`](./CONTRIBUTING.md) — cobre padrão de branches, commits, revisão de PRs e cuidados obrigatórios antes de mergear.

## Equipe

| Área | Responsáveis |
|---|---|
| Backend | Mariah, Filipe |
| Banco de Dados | Luiz Gabriel |
| Gestão do projeto | Luiz Gabriel |

## Entregas

| Entrega | Data | Escopo |
|---|---|---|
| 1ª | 14/10/2026 | MVP Web (PWA) + Backend + Banco de Dados |
| 2ª | 09/12/2026 | React Native + Backend + Banco de Dados + IA |
