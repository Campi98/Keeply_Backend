# Keeply Backend (Spring Boot) → [EN Version](README.en.md)

API REST para gestão de utilizadores e notas que serve a aplicação Android Keeply. Inclui autenticação simples, sincronização de notas (com eliminação lógica) e execução local com base de dados H2 ou via Docker.

## Funcionalidades

- CRUD de Utilizadores e Notas
- Login/Logout simples por email/password
- Eliminação lógica de notas (`isDeleted`) e listagem de IDs eliminados (para sincronização)
- Consola H2 activa para debug (`/h2-console`)

## Tecnologias

- Kotlin + Spring Boot 3 (Web, Data JPA)
- H2 (memória) para desenvolvimento
- Gradle (Kotlin DSL) • JDK 21 • Docker

## Endpoints Principais

- Utilizadores (`/api/users`)
  - `GET /api/users` - Listar utilizadores
  - `POST /api/users` - Criar utilizador
  - `POST /api/users/login` - Login (body: `{ email, password }`)
  - `POST /api/users/logout` - Logout (body: `{ email }`)
  - `PUT /api/users/{id}` - Actualizar utilizador
  - `DELETE /api/users/{id}` - Eliminar utilizador
- Notas (`/api/notes`)
  - `GET /api/notes?userId=` - Listar notas de um utilizador
  - `POST /api/notes` - Criar nota
  - `PUT /api/notes/{id}` - Actualizar nota
  - `DELETE /api/notes/{id}` - Eliminar nota
  - `DELETE /api/notes?userId=` - Eliminar todas as notas de um utilizador

Nota: campos relevantes da nota para sincronização com o frontend: `timestamp`, `synced`, `isDeleted`.

## Como Correr

Pré‑requisitos: JDK 21

- Local (Gradle):

  ```powershell
  .\gradlew.bat bootRun
  ```

  App disponível em http://localhost:8080

- Docker:
  ```powershell
  docker build -t keeply-backend .
  docker run -p 8080:8080 keeply-backend
  ```

## Configuração

- Base de dados H2 em memória (desenvolvimento)
  - URL: `jdbc:h2:mem:testdb`
  - User: `sa` • Password: vazio
  - Consola: `http://localhost:8080/h2-console` (Driver: `org.h2.Driver`)
- Logging SQL activado para debug

## Estrutura (resumo)

```
src/main/kotlin/com/example/demo/
  controller/  (UserController, NoteController)
  model/       (User, Note)
  repository/  (UserRepository, NoteRepository)
  service/     (NoteService)
```

## Ligação ao Frontend

Frontend Android Keeply: https://github.com/Campi98/DAM_Keeply (sincroniza via REST com estes endpoints).
