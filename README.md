# 🏨 Sistema de Gerenciamento de Reservas de Hotel

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de reservas de hotel, usuários e autenticação. O projeto foi desenvolvido com foco em **boas práticas de desenvolvimento backend**, separação de responsabilidades, segurança, validação de dados, tratamento de exceções e persistência em banco de dados relacional.

---

## 📌 Sobre o projeto

O **Sistema de Gerenciamento de Reservas de Hotel** permite controlar reservas de hóspedes através de uma API REST protegida por autenticação e autorização.

O sistema possui dois níveis de acesso:

* `USER` — pode consultar reservas.
* `ADMIN` — possui permissões administrativas, podendo criar, atualizar e excluir reservas, além de executar operações administrativas relacionadas aos usuários.

A autenticação é realizada através de **JWT (JSON Web Token)** e as senhas dos usuários são armazenadas utilizando **hash seguro com PasswordEncoder/BCrypt**.

A aplicação utiliza **PostgreSQL** para persistência dos dados e **JPA/Hibernate** para comunicação com o banco de dados.

---

# 🎯 Objetivos

O projeto foi desenvolvido com os seguintes objetivos:

* Praticar desenvolvimento de APIs REST com Spring Boot.
* Aplicar conceitos de Programação Orientada a Objetos.
* Trabalhar com Spring Data JPA.
* Utilizar PostgreSQL como banco de dados.
* Implementar autenticação e autorização com Spring Security.
* Implementar autenticação baseada em JWT.
* Trabalhar com diferentes níveis de acesso.
* Utilizar DTOs para entrada e saída de dados.
* Aplicar o padrão Mapper.
* Implementar validações com Bean Validation.
* Criar tratamento global de exceções.
* Trabalhar com UUID como identificador.
* Utilizar `BigDecimal` para valores monetários.
* Aplicar separação de responsabilidades entre as camadas.
* Desenvolver uma API organizada e preparada para evolução.

---

# 🚀 Tecnologias utilizadas

| Tecnologia               | Utilização                     |
| ------------------------ | ------------------------------ |
| Java                     | Linguagem principal            |
| Spring Boot              | Framework principal            |
| Spring Web               | Desenvolvimento da API REST    |
| Spring Security          | Segurança e autorização        |
| JWT                      | Autenticação baseada em tokens |
| Spring Data JPA          | Persistência                   |
| Hibernate                | ORM                            |
| PostgreSQL               | Banco de dados                 |
| Bean Validation          | Validação dos dados            |
| BCrypt / PasswordEncoder | Proteção das senhas            |
| Maven                    | Gerenciamento de dependências  |
| UUID                     | Identificação dos recursos     |

---

# 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada na separação de responsabilidades.

```text
src
└── main
    └── java
        └── com.hotel.reservas
            │
            ├── configurations
            │
            ├── controllers
            │
            ├── dto
            │
            ├── entities
            │
            ├── enums
            │
            ├── exceptions
            │
            ├── mapper
            │
            ├── repositories
            │
            └── services
```

Fluxo principal da aplicação:

```text
HTTP Request
     │
     ▼
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
PostgreSQL
```

Para requisições autenticadas:

```text
HTTP Request
     │
     ▼
JWT
     │
     ▼
JwtAuthenticationFilter
     │
     ▼
CustomUserDetailsService
     │
     ▼
PostgreSQL
     │
     ▼
SecurityContext
     │
     ▼
@PreAuthorize
     │
     ▼
Controller / Service
```

---

# 📦 Principais componentes

## Reserva

A entidade `Reserva` representa uma reserva realizada no hotel.

Principais informações:

* ID da reserva
* Nome do hóspede
* Tipo do quarto
* Número de dias
* Valor da diária

O sistema também calcula o valor total da reserva.

```text
valorTotal = numeroDias × valorDiaria
```

---

## Tipo de quarto

O tipo do quarto é representado através de um `enum`, evitando valores arbitrários.

Exemplo:

```java
public enum TipoQuarto {
    STANDARD,
    LUXO,
    PRESIDENCIAL
}
```

A utilização de `enum` permite maior segurança e padronização dos dados.

---

# 👤 Usuários

O sistema possui gerenciamento de usuários e utiliza diferentes níveis de autorização.

Roles disponíveis:

```text
USER
ADMIN
```

As permissões são controladas pelo Spring Security.

### USER

O usuário comum pode:

* consultar reservas;
* buscar reservas por ID;
* atualizar seus próprios dados permitidos.

### ADMIN

O administrador pode:

* criar reservas;
* consultar reservas;
* buscar reservas;
* atualizar reservas;
* excluir reservas;
* executar operações administrativas de usuários.

---

# 🔐 Segurança

A aplicação utiliza **Spring Security** para autenticação e autorização.

O processo de autenticação funciona da seguinte forma:

```text
E-mail + senha
      │
      ▼
AuthenticationManager
      │
      ▼
DaoAuthenticationProvider
      │
      ▼
CustomUserDetailsService
      │
      ▼
PostgreSQL
      │
      ▼
PasswordEncoder
      │
      ▼
Usuário autenticado
      │
      ▼
JWT
```

Depois do login, o cliente recebe um JWT.

Nas próximas requisições, o token deve ser enviado no header:

```http
Authorization: Bearer <TOKEN>
```

---

# 🔑 JWT

O projeto utiliza JWT para autenticação stateless.

O token identifica o usuário através do `subject`, utilizando o e-mail.

Exemplo conceitual:

```text
JWT
 │
 └── subject → email do usuário
```

A aplicação valida:

* assinatura do token;
* validade do token;
* expiração;
* existência atual do usuário.

---

# 🛡️ Validação do usuário no PostgreSQL

Um dos pontos importantes da implementação é que o sistema **não confia exclusivamente nas informações antigas armazenadas no JWT**.

Quando uma requisição autenticada chega:

```text
JWT
 │
 ▼
Extrai e-mail
 │
 ▼
CustomUserDetailsService
 │
 ▼
Busca usuário no PostgreSQL
 │
 ▼
Obtém role atual
 │
 ▼
Cria Authentication
 │
 ▼
SecurityContext
```

Isso permite que alterações realizadas no banco sejam refletidas nas próximas requisições.

### Exemplo

Um usuário recebe um token quando possui:

```text
ROLE_ADMIN
```

Posteriormente, sua role é alterada no banco:

```text
ADMIN → USER
```

Mesmo que o JWT antigo ainda esteja dentro do prazo de validade, a aplicação consulta novamente o usuário no PostgreSQL e obtém:

```text
ROLE_USER
```

Consequentemente, operações protegidas por:

```java
@PreAuthorize("hasRole('ADMIN')")
```

não serão mais autorizadas.

---

# 🔒 Proteção de senhas

As senhas nunca são armazenadas em texto puro.

O projeto utiliza:

```java
PasswordEncoder
```

com hash seguro.

Exemplo conceitual:

```text
Senha informada
       │
       ▼
PasswordEncoder
       │
       ▼
Hash
       │
       ▼
PostgreSQL
```

A senha original não é armazenada nem exibida pela aplicação.

---

# 📋 DTOs

O projeto utiliza DTOs para evitar que as entidades JPA sejam expostas diretamente pela API.

Principais DTOs:

```text
ReservaRequestDTO
ReservaResponseDTO

CriarUsuarioDTO
UsuarioRequestDTO
UsuarioResponseDTO

LoginRequestDTO
LoginResponseDTO
```

### Request DTO

Responsável pelos dados recebidos pela API.

### Response DTO

Responsável pelos dados devolvidos ao cliente.

Essa separação reduz o acoplamento entre a API e as entidades de persistência.

---

# 🔄 Mapper

O projeto utiliza uma camada de Mapper para converter objetos.

Exemplo:

```text
ReservaRequestDTO
       │
       ▼
ReservaMapper
       │
       ▼
Reserva
```

E no retorno:

```text
Reserva
   │
   ▼
ReservaMapper
   │
   ▼
ReservaResponseDTO
```

Isso mantém o Service focado nas regras de negócio.

---

# 🧠 Camada Service

A camada Service concentra as operações e regras de negócio.

Exemplos:

```text
criar()
listarOrdenado()
buscarPorId()
atualizar()
deletar()
```

Também é nessa camada que são aplicadas regras de autorização específicas.

Exemplo:

```java
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
```

ou:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

# 🗄️ Repository

A persistência é realizada através do Spring Data JPA.

Exemplo:

```java
public interface ReservaRepository
        extends JpaRepository<Reserva, UUID> {
}
```

A aplicação utiliza métodos derivados do Spring Data para realizar consultas.

Exemplo utilizado para ordenar reservas:

```java
List<Reserva> findAllByOrderByNumeroDiasDesc();
```

Dessa forma, a ordenação é realizada pelo banco de dados.

---

# 🆔 UUID

As entidades utilizam `UUID` como identificador.

Exemplo:

```java
UUID idReserva
```

No Controller:

```java
@GetMapping("/{idReserva}")
public ResponseEntity<ReservaResponseDTO> buscarPorId(
        @PathVariable UUID idReserva) {
    
    return ResponseEntity.ok(
            reservaService.buscarPorId(idReserva)
    );
}
```

O Spring realiza automaticamente a conversão do valor recebido na URL para `UUID`.

---

# 💰 BigDecimal

Valores monetários são representados utilizando:

```java
BigDecimal
```

em vez de `double`.

Isso evita problemas comuns de precisão em operações financeiras.

Exemplo:

```java
private BigDecimal valorDiaria;
```

---

# ✅ Validação

O projeto utiliza Bean Validation.

Exemplos de validação:

```java
@NotBlank
private String nomeHospede;
```

```java
@NotNull
@Min(1)
private Integer numeroDias;
```

```java
@NotNull
@DecimalMin("0.01")
private BigDecimal valorDiaria;
```

Também é validado o formato do e-mail:

```java
@Email
private String email;
```

---

# ⚠️ Tratamento de exceções

A aplicação possui tratamento global através de:

```java
@RestControllerAdvice
```

O objetivo é evitar tratamento repetitivo de exceções em cada Controller.

Exemplo de fluxo:

```text
Exception
    │
    ▼
GlobalExceptionHandler
    │
    ▼
ErrorResponse
    │
    ▼
HTTP Response
```

O sistema trata situações como:

* recurso não encontrado;
* e-mail já cadastrado;
* operação não permitida;
* erro de validação;
* UUID inválido;
* JSON inválido;
* enum inválido;
* erro de autenticação.

---

# 📄 Resposta padronizada de erro

A aplicação utiliza uma estrutura padronizada:

```java
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {}
```

Exemplo de resposta:

```json
{
  "timestamp": "2026-09-02T16:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Reserva não encontrada.",
  "path": "/reservas/..."
}
```

---

# 🌐 Endpoints

## Autenticação

### Login

```http
POST /auth/login
```

Request:

```json
{
  "email": "usuario@email.com",
  "senha": "senha"
}
```

Response:

```json
{
  "token": "eyJ..."
}
```

O endpoint de login é público.

---

# 🏨 Reservas

## Criar reserva

```http
POST /reservas
```

**Acesso:** `ADMIN`

Exemplo:

```json
{
  "nomeHospede": "João da Silva",
  "tipoQuarto": "STANDARD",
  "numeroDias": 5,
  "valorDiaria": 250.00
}
```

Response:

```json
{
  "idReserva": "UUID",
  "nomeHospede": "João da Silva",
  "tipoQuarto": "STANDARD",
  "numeroDias": 5,
  "valorDiaria": 250.00
}
```

---

## Listar reservas

```http
GET /reservas
```

**Acesso:** `USER` ou `ADMIN`

As reservas são retornadas ordenadas pelo número de dias em ordem decrescente.

```text
5 dias
4 dias
3 dias
2 dias
1 dia
```

---

## Buscar reserva por ID

```http
GET /reservas/{idReserva}
```

**Acesso:** `USER` ou `ADMIN`

Exemplo:

```http
GET /reservas/550e8400-e29b-41d4-a716-446655440000
```

---

## Atualizar reserva

```http
PUT /reservas/{idReserva}
```

**Acesso:** `ADMIN`

---

## Excluir reserva

```http
DELETE /reservas/{idReserva}
```

**Acesso:** `ADMIN`

Response:

```http
204 No Content
```

---

# 👥 Matriz de autorização

| Endpoint                | USER | ADMIN |
| ----------------------- | :--: | :---: |
| `POST /auth/login`      |   ✅  |   ✅   |
| `POST /reservas`        |   ❌  |   ✅   |
| `GET /reservas`         |   ✅  |   ✅   |
| `GET /reservas/{id}`    |   ✅  |   ✅   |
| `PUT /reservas/{id}`    |   ❌  |   ✅   |
| `DELETE /reservas/{id}` |   ❌  |   ✅   |

---

# 🔄 Fluxo de uma requisição autenticada

Exemplo:

```http
GET /reservas
Authorization: Bearer eyJ...
```

Fluxo:

```text
Cliente
   │
   ▼
Spring Security
   │
   ▼
JwtAuthenticationFilter
   │
   ├── Token ausente?
   │       └── não autenticado
   │
   ├── Token inválido?
   │       └── não autenticado
   │
   └── Token válido
           │
           ▼
      Extrai e-mail
           │
           ▼
CustomUserDetailsService
           │
           ▼
      PostgreSQL
           │
           ▼
   Authorities atuais
           │
           ▼
   SecurityContext
           │
           ▼
      @PreAuthorize
           │
           ▼
       Controller
           │
           ▼
        Service
           │
           ▼
       Repository
           │
           ▼
      PostgreSQL
```

---

# 🧩 Princípios aplicados

O projeto busca aplicar boas práticas de desenvolvimento, incluindo:

### Separation of Concerns

Cada camada possui uma responsabilidade específica.

```text
Controller → HTTP
Service → Regras de negócio
Repository → Persistência
Mapper → Conversão
DTO → Contrato da API
Security → Autenticação/autorização
Exception Handler → Tratamento de erros
```

### Baixo acoplamento

As camadas são organizadas de forma que alterações em uma parte da aplicação tenham menor impacto nas demais.

### Injeção de dependências

O projeto utiliza o mecanismo de Dependency Injection do Spring.

Exemplo:

```java
public ReservaService(
        ReservaRepository reservaRepository,
        ReservaMapper reservaMapper) {

    this.reservaRepository = reservaRepository;
    this.reservaMapper = reservaMapper;
}
```

### Programação orientada a interfaces

Interfaces do Spring, como:

```java
JpaRepository
UserDetailsService
```

são utilizadas para reduzir acoplamento e facilitar evolução e testes.

---

# 🛠️ Como executar o projeto

## Pré-requisitos

Antes de executar a aplicação, é necessário possuir:

* Java instalado;
* Maven;
* PostgreSQL;
* IDE de sua preferência.

---

## 1. Clone o projeto

```bash
git clone URL_DO_SEU_REPOSITORIO
```

Entre na pasta:

```bash
cd nome-do-projeto
```

---

## 2. Configure o PostgreSQL

Crie um banco de dados para a aplicação.

Exemplo:

```sql
CREATE DATABASE hotel_reservas;
```

Configure as informações de conexão no arquivo:

```text
application.properties
```

ou:

```text
application.yml
```

---

## 3. Configure as variáveis sensíveis

A aplicação utiliza informações que não devem ser versionadas no GitHub.

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hotel_reservas
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

jwt.secret=SUA_CHAVE_SECRETA
jwt.expiration=3600000
```

> **Importante:** não publique senhas, chaves JWT ou outras credenciais no repositório.

Recomenda-se utilizar variáveis de ambiente em ambientes reais.

---

## 4. Execute a aplicação

Com Maven:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Ou execute a classe principal diretamente pela IDE.

---

# 🔐 Autenticação da API

Após iniciar a aplicação:

### 1. Faça login

```http
POST /auth/login
```

Informe:

```json
{
  "email": "seu@email.com",
  "senha": "suaSenha"
}
```

### 2. Copie o JWT

A API retornará:

```json
{
  "token": "eyJ..."
}
```

### 3. Envie o token nas requisições

```http
Authorization: Bearer eyJ...
```

---

# 🧪 Testes manuais

A API pode ser testada utilizando ferramentas como:

* Postman
* Insomnia
* cURL
* Swagger/OpenAPI, caso configurado

Exemplo com cURL:

```bash
curl -X GET http://localhost:8080/reservas \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

# 🔒 Comportamento de segurança

A API diferencia erros de autenticação e autorização.

### 401 Unauthorized

Ocorre quando o usuário não está autenticado.

Exemplos:

* JWT ausente;
* JWT inválido;
* JWT expirado;
* usuário não encontrado.

### 403 Forbidden

Ocorre quando o usuário está autenticado, mas não possui permissão para realizar determinada operação.

Exemplo:

```text
USER
  │
  └── POST /reservas
          │
          ▼
        403
```

Enquanto:

```text
ADMIN
  │
  └── POST /reservas
          │
          ▼
        201
```

---

# 📊 Estrutura conceitual

```text
                 ┌──────────────────┐
                 │      Cliente     │
                 │ Postman / Front  │
                 └────────┬─────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │   Spring Boot    │
                 └────────┬─────────┘
                          │
              ┌───────────▼───────────┐
              │   Spring Security     │
              │                       │
              │ JWT Authentication    │
              │ Authorization         │
              └───────────┬───────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │    Controller    │
                 └────────┬─────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │     Service      │
                 └────────┬─────────┘
                          │
                  ┌───────▼───────┐
                  │    Mapper     │
                  └───────┬───────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │    Repository    │
                 └────────┬─────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │   PostgreSQL     │
                 └──────────────────┘
```

---

# 📚 Conhecimentos demonstrados

Este projeto demonstra conhecimentos em:

* Java;
* Programação Orientada a Objetos;
* Spring Boot;
* Spring MVC;
* REST;
* Spring Data JPA;
* Hibernate;
* PostgreSQL;
* SQL;
* DTO;
* Mapper;
* Dependency Injection;
* Bean Validation;
* Exception Handling;
* Spring Security;
* Authentication;
* Authorization;
* JWT;
* Password Hashing;
* UUID;
* BigDecimal;
* Enum;
* HTTP Status Codes;
* arquitetura em camadas;
* boas práticas de desenvolvimento backend.

---

# 🚧 Possíveis evoluções

O projeto está funcionalmente completo, mas pode ser evoluído futuramente com:

* Testes unitários com JUnit e Mockito;
* Testes de integração;
* Swagger/OpenAPI;
* Docker e Docker Compose;
* Paginação;
* Filtros avançados de reservas;
* Sistema de check-in/check-out;
* Disponibilidade de quartos;
* Cadastro de quartos;
* Controle de diferentes categorias de quartos;
* Datas de entrada e saída;
* Sistema de cancelamento de reservas;
* Refresh Token;
* Revogação de tokens;
* Auditoria de operações;
* Logs estruturados;
* CI/CD;
* Deploy em ambiente cloud.

Essas funcionalidades são extensões futuras e não são necessárias para o funcionamento atual do sistema.

---

# 🎓 Objetivo de aprendizado

Este projeto foi desenvolvido como uma aplicação prática para consolidar conhecimentos de desenvolvimento backend utilizando Java e Spring Boot.

A implementação buscou ir além de um CRUD simples, incorporando:

```text
CRUD
 +
DTOs
 +
Mapper
 +
JPA
 +
PostgreSQL
 +
Validation
 +
Exception Handling
 +
Spring Security
 +
JWT
 +
Authorization
 +
Arquitetura em camadas
```

O resultado é uma API REST estruturada para representar um cenário próximo ao encontrado em aplicações backend reais.

---

# 👨‍💻 Autor

**Rodrigo Marques Viana**

Projeto desenvolvido para estudo, prática e consolidação de conhecimentos em desenvolvimento backend com Java e Spring Boot.

---

# 📄 Licença

Este projeto pode ser utilizado para fins de estudo e aprendizado.
