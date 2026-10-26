# Saúde na Palma da Mão — Backend

API REST do Projeto Integrador **Saúde na Palma da Mão**, também referenciado como **Saúde Recife** nos materiais acadêmicos. O projeto tem como objetivo apoiar a consulta de informações de clínicas e profissionais de saúde, além do agendamento de consultas, com foco inicial no público idoso (60+ anos).

## Sobre o projeto

O sistema contempla o cadastro e a consulta de clínicas, profissionais de saúde, especialidades, pacientes e usuários, além de funcionalidades relacionadas à disponibilidade e ao gerenciamento de agendamentos.

A primeira entrega contempla o MVP Web (PWA), o backend e o banco de dados. A segunda entrega prevê a evolução para React Native e a inclusão de recursos de pré-triagem inteligente com IA.

**Repositórios relacionados:**
- Front-end PWA: [saude-recife-pwa](https://github.com/LuizSanti/saude-recife-pwa)
- Backend: [saude-recife-back](https://github.com/LuizSanti/saude-recife-back)

## Tecnologias utilizadas

- **Linguagem:** Java
- **Framework:** Spring Boot
- **Banco de dados:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Build:** Maven
- **Versionamento:** Git e GitHub
- **Hospedagem do backend:** Render
- **Hospedagem do front-end:** Vercel

## Estrutura do projeto

```text
saude-recife-back/
├── db/
│   ├── schema.sql
│   ├── seed.sql
│   ├── scrum15_disponibilidade.sql
│   ├── agenda.sql
│   ├── rf11_indice_parcial.sql
│   └── agenda_validacoes.sql
├── docs/
├── bruno/
├── src/
│   ├── main/
│   │   ├── java/                 # Código da aplicação
│   │   │   ├── .../controller    # Endpoints REST
│   │   │   ├── .../service       # Regras de negócio
│   │   │   ├── .../repository    # Acesso aos dados
│   │   │   └── .../model         # Entidades JPA
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Testes automatizados
├── pom.xml
└── README.md
```

A organização interna das classes Java pode evoluir conforme a implementação das funcionalidades e a integração entre os integrantes da equipe.

## Banco de dados

O projeto utiliza PostgreSQL, com scripts SQL versionados no diretório `db/` para estruturar e preparar os dados.

### Modelagem relacional

O esquema-base contempla as seguintes tabelas:

| Tabela | Finalidade |
|---|---|
| `clinica` | Cadastro e informações das clínicas |
| `especialidade` | Especialidades médicas |
| `profissional` | Cadastro dos profissionais de saúde |
| `paciente` | Cadastro dos pacientes |
| `usuario` | Informações de usuários e autenticação |
| `clinica_especialidade` | Relacionamento entre clínicas e especialidades |
| `profissional_clinica` | Associação entre profissionais e clínicas |
| `profissional_especialidade` | Associação entre profissionais e especialidades |

A modelagem utiliza chaves primárias, chaves estrangeiras e restrições de unicidade para manter a integridade dos dados e dos relacionamentos.

O esquema também contempla restrições relacionadas a registros duplicados, incluindo o controle de CNES das clínicas e de informações cadastrais, conforme as regras definidas no banco.

### Scripts SQL

#### `db/schema.sql`

Define a estrutura-base do banco de dados, incluindo tabelas, sequências, chaves primárias, chaves estrangeiras e restrições de integridade.

#### `db/seed.sql`

Contém dados iniciais para apoiar a preparação e a validação do ambiente de desenvolvimento. A versão utilizada no projeto contempla dados demonstrativos de clínicas, especialidades e seus relacionamentos.

Os dados de seed devem ser tratados como dados de desenvolvimento, e não como comprovação de que os mesmos registros estejam presentes no banco de produção.

#### `db/scrum15_disponibilidade.sql`

Script complementar relacionado à disponibilidade dos profissionais de saúde. Deve ser consultado antes da preparação do banco para essa funcionalidade, respeitando as dependências e a estrutura existente no ambiente.

#### `db/agenda.sql`

Define estruturas complementares para agenda e agendamentos, incluindo a restrição `ex_agendamento_profissional_periodo`, destinada a impedir a sobreposição de períodos de agendamentos confirmados para o mesmo profissional.

#### `db/rf11_indice_parcial.sql`

Implementa o índice parcial adicional relacionado à regra RF11, vinculada à tarefa SCRUM-50. A regra busca impedir agendamentos não cancelados do mesmo profissional com o mesmo horário de início.

Esse script depende das estruturas criadas em `db/agenda.sql` e deve ser aplicado posteriormente, após verificar a compatibilidade com o banco de destino.

#### `db/agenda_validacoes.sql`

Reúne consultas de validação somente de leitura para verificar estruturas, restrições e integridade dos dados relacionados à agenda.

### Cuidados com as migrações

Os scripts complementares não substituem `db/schema.sql` nem `db/seed.sql`.

Antes de aplicar scripts em outro ambiente:

1. Faça um backup do banco de destino.
2. Confirme a estrutura das tabelas e as chaves estrangeiras existentes.
3. Verifique as dependências entre os scripts.
4. Execute as migrações na ordem adequada.
5. Rode as consultas de validação.
6. Confirme a compatibilidade com as entidades e os repositórios utilizados pela aplicação.

A extensão PostgreSQL `btree_gist`, necessária para determinadas restrições de exclusão, pode exigir permissões administrativas para instalação.

**Importante:** a criação de tabelas, índices e restrições no banco não garante, por si só, que os endpoints correspondentes estejam implementados ou que os conflitos sejam tratados corretamente pela API. Esse comportamento deve ser validado na camada de aplicação.

## Preparação e qualidade dos dados

Durante o desenvolvimento, também foram considerados cuidados com a consistência e a rastreabilidade dos dados das clínicas:

- Armazenamento do CNES como texto, preservando zeros à esquerda.
- Tratamento de CNPJ ausente utilizando `NULL`, sem inventar valores cadastrais.
- Identificação de códigos utilizados como placeholders.
- Registro da origem e da data de referência dos dados importados.
- Identificação de informações demonstrativas, como horários utilizados para fins de teste.
- Validação de registros e relacionamentos por meio de consultas SQL.

Esses cuidados ajudam a diferenciar dados de origem, dados ausentes e informações utilizadas apenas para demonstração.

## Configuração do banco de dados

O schema é versionado por scripts SQL. Em ambientes de produção, não se recomenda utilizar `ddl-auto=create` ou `ddl-auto=update` sem uma estratégia controlada de migração.

Quando compatível com o esquema e as entidades implementadas, recomenda-se utilizar:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Essa configuração permite ao Hibernate verificar a compatibilidade entre as entidades JPA e a estrutura do banco, sem criar ou alterar automaticamente as tabelas.

### Passos para preparar o banco

1. Instale e inicie o PostgreSQL.
2. Crie um banco de dados local, por exemplo, `saude_recife`.
3. Aplique `db/schema.sql` em um banco vazio.
4. Execute `db/seed.sql` quando precisar dos dados iniciais de desenvolvimento.
5. Aplique os scripts complementares conforme as dependências das funcionalidades que serão testadas.
6. Configure a conexão da aplicação.
7. Execute as consultas de validação e confirme a integridade dos dados.

Se o banco já possuir tabelas ou registros, não execute todos os scripts indiscriminadamente. Verifique primeiro a estrutura existente e faça um backup.

## Como executar localmente

### Pré-requisitos

- Java 17 ou versão compatível com o `pom.xml` do projeto.
- Maven, ou utilização do Maven Wrapper incluído no repositório.
- PostgreSQL acessível.
- Git.

### Clonar o repositório

```bash
git clone https://github.com/LuizSanti/saude-recife-back.git
cd saude-recife-back
```

### Configurar a conexão

Configure as propriedades de conexão em `src/main/resources/application.properties` ou utilize as variáveis de ambiente adotadas pela aplicação.

Exemplo de configuração local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/saude_recife
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

Os nomes das propriedades devem corresponder à configuração efetivamente utilizada pelo projeto. Não versione senhas reais, tokens ou outras credenciais.

### Executar a aplicação

Com Maven instalado:

```bash
mvn spring-boot:run
```

Ou, no Windows, caso o Maven Wrapper esteja disponível:

```powershell
.\mvnw.cmd spring-boot:run
```

Por padrão, a aplicação Spring Boot utiliza a porta `8080`, salvo configuração diferente.

### Executar os testes

```bash
mvn test
```

A execução dos testes deve ser utilizada para verificar o comportamento implementado. A conclusão do build, isoladamente, não comprova que todas as funcionalidades estejam integradas.

## Variáveis de configuração

| Variável | Finalidade |
|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexão com o PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco de dados |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco de dados |

As variáveis devem ser configuradas no ambiente de execução conforme a forma como o projeto carrega suas propriedades.

Nunca publique credenciais reais no repositório. Utilize variáveis de ambiente e mantenha arquivos locais com informações sensíveis fora do controle de versão.

## Integração entre front-end, API e banco de dados

A arquitetura prevista para o projeto utiliza o PWA como cliente, a API REST como camada de comunicação e o PostgreSQL para persistência dos dados.

```text
Front-end PWA
     |
     | HTTP / JSON
     v
API REST — Spring Boot
     |
     | Spring Data JPA / Hibernate
     v
PostgreSQL
```

O front-end publicado utiliza a variável de ambiente `VITE_API_URL` para indicar o endereço da API e `VITE_USE_MOCK` para controlar o uso de dados simulados, conforme a implementação do cliente.

Na configuração de produção utilizada durante os testes:

- `VITE_API_URL` aponta para `https://saude-recife-back.onrender.com`.
- `VITE_USE_MOCK` está configurada como `false`.

O front-end foi publicado na Vercel. Durante a preparação da publicação, foi corrigido um problema de importação causado pela diferença entre letras maiúsculas e minúsculas no nome de um arquivo, permitindo a conclusão do build.

A comunicação de ponta a ponta, especialmente o login e a persistência das operações no ambiente publicado, ainda precisa ser validada por meio de testes das requisições HTTP e das respostas da API.

## Status da implementação

O desenvolvimento contempla a estrutura do banco de dados, os scripts de preparação de dados e as migrações SQL complementares para funcionalidades de disponibilidade e agendamento.

O backend também foi iniciado localmente com sucesso durante os testes de preparação do ambiente.

Entretanto, a publicação do front-end e a inicialização da aplicação não devem ser interpretadas como comprovação de que todas as funcionalidades estejam operacionais em produção.

Os seguintes pontos precisam ser verificados por meio de testes integrados:

- Autenticação com credenciais válidas.
- Comunicação entre o front-end publicado e a API.
- Cadastro, consulta e atualização dos registros.
- Persistência e recuperação dos dados no banco utilizado pelo ambiente publicado.
- Disponibilidade e agendamento de consultas.
- Tratamento de conflitos de horário e cancelamento de agendamentos.
- Autorização conforme os perfis de usuário.

O status deve ser atualizado à medida que cada funcionalidade for validada.

## Documentação da API

A documentação automática com Swagger/OpenAPI é recomendada para facilitar a consulta aos endpoints, parâmetros, formatos de requisição e respostas da API.

A disponibilidade e a configuração dessa documentação devem ser confirmadas na implementação atual do backend antes de divulgar um endereço de Swagger como funcional.

## Deploy

### Front-end

Aplicação PWA publicada na Vercel, com configuração de ambiente para apontar para a API.

Repositório: [saude-recife-pwa](https://github.com/LuizSanti/saude-recife-pwa)

### Backend

Serviço hospedado no Render:

`https://saude-recife-back.onrender.com`

O endereço do serviço não garante que todas as rotas estejam disponíveis ou que o banco esteja corretamente conectado. A disponibilidade dos endpoints e a integração com o banco de produção devem ser confirmadas durante os testes.

### Banco de dados

A preparação do banco utiliza PostgreSQL e scripts SQL versionados no repositório. A existência de um banco local não implica que ele seja o mesmo banco utilizado pelo serviço publicado.

A configuração de produção deve ser verificada separadamente, com atenção às credenciais, à conectividade, ao schema e aos dados efetivamente persistidos.

## Contribuição e versionamento

O projeto utiliza Git e GitHub para versionamento e revisão das alterações.

Antes de abrir um Pull Request, consulte o [`CONTRIBUTING.md`](./CONTRIBUTING.md), quando disponível, para conhecer as convenções de branches, commits, revisão de código e integração das alterações.

As mudanças de schema e os scripts complementares devem ser versionados junto ao projeto para facilitar a reprodução do ambiente e a revisão das migrações.

## Equipe

| Área | Responsáveis |
|---|---|
| Backend | Mariah e Filipe |
| Banco de Dados | Luiz Gabriel |
| Gestão do projeto | Luiz Gabriel |

## Entregas previstas

| Entrega | Data | Escopo |
|---|---|---|
| 1ª entrega | 14/10/2026 | MVP Web (PWA), backend e banco de dados |
| 2ª entrega | 09/12/2026 | React Native, backend, banco de dados e IA |

## Próximos passos técnicos

- Validar a comunicação de ponta a ponta entre PWA, API REST e PostgreSQL.
- Concluir a verificação do fluxo de autenticação no ambiente publicado.
- Confirmar a implementação e o comportamento dos endpoints de cadastro, consulta e manutenção.
- Validar os endpoints de disponibilidade e agendamento em conjunto com as migrações SQL.
- Testar conflitos de horário, cancelamentos e integridade dos registros.
- Confirmar a documentação dos endpoints e a cobertura dos testes automatizados.
- Manter o README alinhado ao estado efetivamente validado da aplicação.
