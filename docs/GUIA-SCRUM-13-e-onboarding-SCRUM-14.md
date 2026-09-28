# Guia do Backend — SCRUM-13 + SCRUM-41 (o que foi feito) e SCRUM-14 (como continuar)

Este documento explica o que foi construído nas tasks **SCRUM-13** (Setup do backend + autenticação) e **SCRUM-41** (CRUD de Usuario com controle de acesso) e serve como guia passo a passo para quem for implementar a **SCRUM-14** (API de cadastro: clínicas, profissionais, especialidades, pacientes), seguindo exatamente o mesmo padrão.

Se você é novo(a) em Java, IntelliJ e arquitetura hexagonal, leia este documento na ordem — cada seção assume que você leu a anterior.

**Atualização importante:** a estrutura de pacotes mudou desde a primeira versão deste guia (migramos para alinhar com uma referência externa). Os nomes corretos hoje são `adapter/input` e `adapter/output` (não `in`/`out`), e as Ports vivem em `application/port`, não em `domain/port`. Este documento já reflete a versão atual.

---

## 1. Como rodar o projeto na sua máquina

### Pré-requisitos
- Java 17 instalado
- IntelliJ IDEA
- Docker Desktop instalado e aberto (o "engine" precisa estar rodando, veja o ícone da baleia na bandeja do sistema)

### Passo a passo

1. Clone o repositório e abra a pasta no IntelliJ.
2. Suba o banco de dados (na raiz do projeto, onde está o `docker-compose.yml`):
   ```bash
   docker compose up -d
   ```
3. Confirme que o container subiu:
   ```bash
   docker ps
   ```
   Deve aparecer `saude-recife-db` com status `Up`.
4. Se for a **primeira vez** rodando (banco vazio), aplique o schema e o seed de teste:
   ```bash
   docker cp db/schema.sql saude-recife-db:/schema.sql
   docker exec -it saude-recife-db psql -U saude_user -d saude_recife -f /schema.sql

   docker cp db/seed.sql saude-recife-db:/seed.sql
   docker exec -it saude-recife-db psql -U saude_user -d saude_recife -f /seed.sql
   ```
   O `seed.sql` cria 2 usuários de teste: um `PACIENTE` (`teste@saude.com` / `123456`) e um `ADMIN` (`admin@saude.com` / `admin123`) — você vai precisar do admin pra testar qualquer rota de `/usuarios/**`.
5. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
6. Se aparecer `Started ApiApplication in X seconds` sem erro, está tudo funcionando.

### Testando o login e as rotas protegidas

Abra o **Bruno** (app gratuito para testar APIs, similar ao Postman — [usebruno.com](https://www.usebruno.com/downloads)), abra a collection que está na pasta `bruno/` do repositório, e rode a request **Login** com:
```json
{
  "email": "admin@saude.com",
  "senha": "admin123"
}
```
Deve retornar `200 OK` com um token JWT. Copie esse token — qualquer rota além de `/auth/**` exige ele no header:

```
Authorization: Bearer <token copiado>
```

Sem esse header (ou com um token de usuário sem o perfil certo), rotas protegidas retornam `401 Unauthorized` (sem token válido) ou `403 Forbidden` (token válido, mas perfil sem permissão).

### Comandos úteis do dia a dia no IntelliJ

| O que você quer fazer | Como fazer |
|---|---|
| Renomear um arquivo/classe sem quebrar imports | Botão direito no arquivo → **Refactor → Rename** (nunca renomeie arrastando ou pelo explorador do Windows) |
| Mover um arquivo de pacote sem quebrar imports | Botão direito no arquivo → **Refactor → Move** |
| Corrigir um `import` faltando | Clique na linha com erro (sublinhado vermelho) → `Alt + Enter` → escolha o import sugerido |
| Recarregar dependências depois de editar o `pom.xml` | Ícone circular de "reload" do Maven (aparece geralmente no canto superior direito, ou `Ctrl+Shift+O`) |
| Rodar a aplicação | Botão de play verde ao lado da classe `ApiApplication`, ou `./mvnw spring-boot:run` no terminal |

---

## 2. O que é arquitetura hexagonal (resumo prático)

A ideia central: **o código que representa as regras de negócio (`domain`) nunca deve depender de framework nenhum** (nem Spring, nem JPA, nem banco). Quem depende do domínio é o resto — nunca o contrário.

Isso é organizado em 4 camadas, cada uma numa pasta:

```
com.saude.recife.api
├── domain           → regras de negócio, Java puro, sem anotações
├── application       → orquestra o domínio (ports + services)
├── adapter            → conecta com o mundo externo (banco, web)
└── infrastructure    → configurações técnicas do Spring
```

### O fluxo de uma requisição (do início ao fim)

```
1. Requisição HTTP chega no Controller       (adapter/input/web/controller)
2. Controller chama a UseCase (interface)     (application/port/input)
3. Quem implementa essa interface é o Service (application/service)
4. Service usa uma Port de saída (interface)  (application/port/output)
5. Quem implementa essa Port é o Adapter      (adapter/output/persistence)
6. Adapter fala de verdade com o banco        (via Spring Data JPA)
```

**Regra de ouro:** as setas de dependência sempre apontam para dentro (adapter → application → domain), nunca o contrário. Isso significa: se um dia trocarem PostgreSQL por outro banco, ou REST por GraphQL, o `domain` não muda nada.

### Por que isso dá tanto arquivo pra uma coisa só

Sim, é normal parecer "muita coisa pra um simples cadastro". A vantagem aparece na manutenção: cada arquivo tem uma responsabilidade única e pequena, fica fácil testar cada peça isolada, e trocar uma tecnologia não afeta o resto.

---

## 3. Exemplo guiado: como o `Usuario` foi implementado (modelo para o SCRUM-14)

Esta seção mostra, **arquivo por arquivo, na ordem em que foram criados**, tudo que compõe a funcionalidade de `Usuario`. Use isso como checklist ao criar `Clinica`, `Profissional`, `Especialidade` e `Paciente`.

### 3.1. `domain/model/Usuario.java` — o objeto de domínio puro

Representa "o que é um Usuário" nas regras de negócio. Sem `@Entity`, sem `@Column`, sem nada de framework — só Java puro (atributos, construtor, getters).

📁 `domain/model/Usuario.java`

### 3.2. `domain/model/TipoUsuario.java` — enum de domínio

Enum simples, Java puro, representando os valores válidos (`PACIENTE`, `PROFISSIONAL`, `ADMIN`).

📁 `domain/model/TipoUsuario.java`

### 3.3. `domain/exception/` — exceções de negócio

Exceções que representam erros de regra de negócio (ex: `CredenciaisInvalidasException`, `UsuarioInativoException`). São Java puro — não sabem o que é HTTP. Quem traduz pra status HTTP é o `GlobalExceptionHandler` (seção 3.9).

📁 `domain/exception/CredenciaisInvalidasException.java`
📁 `domain/exception/UsuarioInativoException.java`

### 3.4. `application/port/output/UsuarioPort.java` — contrato de persistência

Interface que declara **o que** o domínio precisa do banco (salvar, buscar por email, etc.) sem dizer **como** isso é feito.

```java
public interface UsuarioPort {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    boolean existePorEmail(String email);
}
```

### 3.5. Camada de persistência — 4 arquivos que trabalham juntos

📁 `adapter/output/persistence/entity/UsuarioEntity.java`
A entidade JPA de verdade, com `@Entity`, `@Table`, `@Column` — espelha a tabela do banco. **É diferente da classe de domínio** (seção 3.1) — propositalmente.

📁 `adapter/output/persistence/repository/UsuarioRepository.java`
Interface que estende `JpaRepository<UsuarioEntity, Long>` — o Spring Data gera as queries automaticamente.

📁 `adapter/output/persistence/mapper/UsuarioPersistenceMapper.java`
Converte `Usuario` (domínio) ↔ `UsuarioEntity` (JPA) e vice-versa.

📁 `adapter/output/persistence/adapter/UsuarioPersistenceAdapter.java`
Implementa a `UsuarioPort`, usando o `Repository` + `Mapper` por trás.

### 3.6. `application/port/input/` — o caso de uso

📁 `application/port/input/AutenticarUsuarioUseCase.java`
Interface que representa "o que o mundo externo pode pedir" ao domínio.

📁 `application/port/input/ResultadoAutenticacao.java`
Objeto pequeno que representa o resultado de uma autenticação (token + tipo de usuário).

### 3.7. `application/service/AutenticacaoService.java` — a implementação do caso de uso

Implementa a interface acima. Orquestra as ports (`UsuarioPort`, `CriptografiaPort`, `TokenPort`) para executar a lógica de autenticação. **Java puro — sem `@Service`, sem import de Spring.**

### 3.8. Camada web (`adapter/input/web/`)

📁 `adapter/input/web/dto/request/LoginRequest.java` e `dto/response/LoginResponse.java`
DTOs — objetos simples (usando `record` do Java) que representam o corpo das requisições/respostas HTTP.

📁 `adapter/input/web/mapper/AutenticacaoWebMapper.java`
Converte o resultado do domínio (`ResultadoAutenticacao`) para o DTO de resposta (`LoginResponse`).

📁 `adapter/input/web/controller/AutenticacaoController.java`
O controller REST propriamente dito — recebe a requisição HTTP, chama a `UseCase`, devolve a resposta via o mapper.

### 3.9. `adapter/input/web/exception/GlobalExceptionHandler.java`

Captura as exceções de domínio (seção 3.3) e traduz para status HTTP (`401`, `403`, etc.).

### 3.10. `infrastructure/config/` — conectando tudo ao Spring

📁 `infrastructure/config/AutenticacaoConfig.java`
Classe `@Configuration` que registra o `AutenticacaoService` como um Bean do Spring, injetando as 3 ports que ele precisa.

📁 `infrastructure/config/SecurityConfig.java`
Configura quais rotas são públicas (`/auth/**`), quais exigem só autenticação, e quais exigem um perfil específico (`/usuarios/**` → só `ADMIN`).

---

## 4. Como funciona a autorização por perfil (SCRUM-41)

Login gera um token, mas **alguém precisa validar esse token em cada requisição seguinte** e descobrir "quem é" e "qual o perfil dele" — senão não tem como o Spring aplicar regras como "só ADMIN pode fazer isso". Essa peça é o `JwtAuthenticationFilter`.

### 4.1. `application/port/output/TokenPort.java` — expandido

Além de `gerar(usuario)` (que já existia), ganhou `validar(token)`, que devolve um `TokenPayload` (email + tipo de usuário) se o token for válido, ou vazio se não for.

📁 `application/port/output/TokenPayload.java` — um `record` simples: `(String email, TipoUsuario tipoUsuario)`

### 4.2. `adapter/output/security/JwtTokenAdapter.java` — implementação de `validar`

Decodifica o token JWT e extrai o payload. Se o token estiver expirado, adulterado ou mal formado, captura a exceção e devolve `Optional.empty()` — um token inválido é uma situação esperada (usuário deslogado), não um erro de sistema.

### 4.3. `infrastructure/security/JwtAuthenticationFilter.java` — o filtro em si

Um `OncePerRequestFilter` do Spring que roda **antes de qualquer controller**. Lê o header `Authorization: Bearer <token>`, valida via `TokenPort`, e se for válido, registra no `SecurityContextHolder` que aquela requisição está autenticada, com uma "role" no formato `ROLE_ADMIN`, `ROLE_PACIENTE` etc. (prefixo `ROLE_` é uma convenção que o Spring Security exige).

**Por que esse arquivo está em `infrastructure/security/` e não em `adapter`?** Um filtro HTTP é "cabo de força" do próprio Spring — ele não implementa nenhuma Port, só orquestra uma (`TokenPort`) pra plugar no mecanismo de segurança do framework. Por isso ficou junto de `infrastructure`, ao lado de `config`.

### 4.4. `infrastructure/config/SecurityConfig.java` — registrando o filtro e as regras de perfil

```java
.authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/usuarios/**").hasRole("ADMIN")
        .anyRequest().authenticated()
)
.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```

`hasRole("ADMIN")` compara com a role que o filtro registrou. Se quiser restringir uma rota nova sua a um perfil específico, basta adicionar uma linha nesse mesmo padrão — **antes** da linha `.anyRequest().authenticated()` (a ordem importa: regras mais específicas primeiro).

---

## 5. Exemplo guiado: CRUD completo de Usuario (SCRUM-41)

Diferente da SCRUM-13 (só login), a SCRUM-41 implementou o CRUD completo de `Usuario` — esse é hoje o exemplo mais completo do projeto, e o que você deve seguir de perto, porque já inclui validações de negócio e controle de acesso.

### 5.1. `domain/model/Usuario.java` — métodos de negócio adicionados

```java
public void desativar() {
    this.ativo = false;
}

public void atualizarDados(String nome, String telefone, TipoUsuario tipoUsuario) {
    this.nome = nome;
    this.telefone = telefone;
    this.tipoUsuario = tipoUsuario;
}
```

Continua sem setters públicos soltos — só métodos que expressam uma intenção de negócio clara.

### 5.2. Duas novas exceções de domínio

📁 `domain/exception/UsuarioNaoEncontradoException.java` → vira `404 Not Found`
📁 `domain/exception/EmailJaCadastradoException.java` → vira `409 Conflict`

### 5.3. `application/port/output/UsuarioPort.java` — expandida

Ganhou `atualizar`, `desativar` e `listarTodos`, além dos métodos que já existiam.

### 5.4. `application/port/input/UsuarioUseCase.java` — uma única interface por entidade

Diferente do padrão da SCRUM-13 (uma interface por ação, ex: só `AutenticarUsuarioUseCase`), aqui seguimos o padrão da referência externa: **uma interface por entidade**, agregando todos os métodos do CRUD (`cadastrar`, `atualizar`, `desativar`, `buscarPorId`, `listarTodos`). Use esse padrão pra `ClinicaUseCase`, `PacienteUseCase` etc.

**Importante:** os métodos recebem parâmetros soltos (`String nome, String email...`), nunca um DTO — DTOs pertencem à camada web, e essa interface é de `application`, uma camada mais interna.

### 5.5. `application/service/UsuarioService.java` — a lógica de negócio

Pontos que valem atenção:
- Verifica e-mail duplicado **antes** de salvar (`existePorEmail`), lançando `EmailJaCadastradoException` — assim o erro vira uma resposta HTTP clara (`409`), em vez de estourar como erro de banco (`500`)
- Usa `CriptografiaPort` pra transformar a senha em hash antes de salvar — nunca salva senha em texto puro
- `desativar` busca o usuário (pra confirmar que existe e retornar `404` se não), depois delega a regra em si pro método `usuario.desativar()` do domínio

### 5.6. Camada web completa

📁 `adapter/input/web/dto/request/CadastrarUsuarioRequest.java` e `AtualizarUsuarioRequest.java`
📁 `adapter/input/web/dto/response/UsuarioResponse.java` — **nunca inclui senha nem hash de senha** na resposta
📁 `adapter/input/web/mapper/UsuarioWebMapper.java`
📁 `adapter/input/web/controller/UsuarioController.java` — rotas REST completas:

| Método | Rota | Ação |
|---|---|---|
| POST | `/usuarios` | Cadastrar |
| GET | `/usuarios` | Listar todos |
| GET | `/usuarios/{id}` | Buscar por id |
| PUT | `/usuarios/{id}` | Atualizar |
| PATCH | `/usuarios/{id}/desativar` | Desativar |

**Por que `PATCH .../desativar` em vez de `DELETE`?** Porque não é exclusão real — é inativação (`ativo = false`), então usar o verbo `DELETE` seria enganoso pra quem for consumir a API.

### 5.7. `infrastructure/config/UsuarioConfig.java`

Mesmo padrão do `AutenticacaoConfig.java` — um `@Configuration` por feature.

---

## 6. Passo a passo para o SCRUM-14: criando o CRUD de `Clinica` (exemplo)

Repita esse processo para `Clinica`, `Profissional`, `Especialidade` e `Paciente`. Vou usar `Clinica` como exemplo — troque o nome para as outras entidades.

- [ ] **1.** Criar `domain/model/Clinica.java` (Java puro, atributos baseados no `schema.sql`: nome, cnpj, telefone, email, endereço, ativo)
- [ ] **2.** Criar `application/port/output/ClinicaPort.java` com os métodos que o domínio precisa (`salvar`, `buscarPorId`, `listarTodas`, `buscarPorCnpj`, etc.)
- [ ] **3.** Criar `adapter/output/persistence/entity/ClinicaEntity.java` (com `@Entity`, `@Table(name = "clinica")`, mapeando as colunas)
- [ ] **4.** Criar `adapter/output/persistence/repository/ClinicaRepository.java` (`extends JpaRepository<ClinicaEntity, Long>`)
- [ ] **5.** Criar `adapter/output/persistence/mapper/ClinicaPersistenceMapper.java` (conversão entidade ↔ domínio)
- [ ] **6.** Criar `adapter/output/persistence/adapter/ClinicaPersistenceAdapter.java` (implementa `ClinicaPort`)
- [ ] **7.** Criar `application/port/input/ClinicaUseCase.java` — **uma única interface** agregando todos os métodos (`cadastrar`, `atualizar`, `desativar`, `buscarPorId`, `listarTodos`), seguindo o padrão usado em `UsuarioUseCase` (seção 5.4), não uma interface por ação
- [ ] **8.** Criar `application/service/ClinicaService.java` (implementa a `ClinicaUseCase`, Java puro)
- [ ] **9.** Criar os DTOs em `adapter/input/web/dto/request/` e `dto/response/` (ex: `CadastrarClinicaRequest`, `ClinicaResponse`)
- [ ] **10.** Criar `adapter/input/web/mapper/ClinicaWebMapper.java`
- [ ] **11.** Criar `adapter/input/web/controller/ClinicaController.java` com os endpoints REST (`POST /clinicas`, `GET /clinicas`, `GET /clinicas/{id}`)
- [ ] **12.** Registrar os Beans em `infrastructure/config/ClinicaConfig.java` (siga o modelo do `AutenticacaoConfig.java`)
- [ ] **13.** Adicionar as novas rotas no `SecurityConfig.java` se precisarem de regra de acesso diferente das já existentes
- [ ] **14.** Testar no Bruno, criando novas requests na collection (ex: `Cadastrar Clínica`, `Listar Clínicas`)
- [ ] **15.** Commitar seguindo o padrão: `git commit -m "SCRUM-14: implementa cadastro de clinica"`

Repita o processo para `Profissional`, `Especialidade` e `Paciente` — a estrutura é sempre a mesma, só muda o nome da entidade e os atributos.

---

## 5. Fluxo de trabalho do Git (relembrando o guia do Luiz)

- Nunca trabalhe direto na `main` ou na `develop`
- Nomeie sua branch como `feature/SCRUM-14-descricao-curta`
- Commits pequenos, em português, no imperativo: `git commit -m "SCRUM-14: cria entidade Clinica"`
- PR só depois de finalizar a task inteira (conforme combinado com o Luiz)
- PR sempre para `develop`, nunca para `main`

---

## 7. Pontos de atenção / pendências conhecidas

- **O CRUD de `Usuario` já existe** (SCRUM-41, rotas `/usuarios/**`) — se `Paciente`/`Profissional` precisarem criar um `Usuario` vinculado no mesmo cadastro, esse fluxo já está pronto para reaproveitar (`UsuarioUseCase.cadastrar(...)`), não precisa recriar.
- As rotas de `Usuario` exigem perfil `ADMIN` (via `hasRole("ADMIN")` no `SecurityConfig`). Decida com o time se as rotas de `Clinica`/`Profissional`/`Especialidade`/`Paciente` também precisam dessa restrição, ou se ficam abertas a qualquer autenticado.
- As tabelas `disponibilidade` e `agendamento` (schema já recebido do responsável pela modelagem) ainda não foram implementadas em código — ficaram para uma sprint futura (task própria). Isso não bloqueia o SCRUM-14.
- A chave `jwt.secret` está fixa no `application.properties` — antes do deploy final, isso precisa virar variável de ambiente.
- O `ddl-auto` está como `validate` — ou seja, o Hibernate **nunca cria ou altera tabelas sozinho**, ele só confere se as entidades batem com o schema existente. Se sua entidade JPA não bater com o schema do banco, a aplicação vai falhar ao subir com um erro claro.

---

## 8. Dúvidas?

Se travar em algo específico da arquitetura, procure primeiro o exemplo equivalente na funcionalidade de `Usuario` (seções 3 e 5) — ela cobre 100% do padrão que se repete em qualquer outra entidade, incluindo autenticação, autorização por perfil e CRUD completo. Qualquer dúvida sobre uma decisão de design específica, pergunte antes de implementar diferente — é mais barato alinhar antes do que refazer depois.
