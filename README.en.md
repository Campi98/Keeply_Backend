# Keeply Backend (Spring Boot) → [PT Version](README.md)

REST API for managing users and notes used by the Keeply Android app. It provides simple authentication, notes synchronization (with soft delete), and can run locally with an in‑memory H2 database or via Docker.

## Features

- Users and Notes CRUD
- Simple login/logout via email/password
- Soft delete for notes (`isDeleted`) and listing deleted IDs (for sync)
- H2 console enabled for debugging (`/h2-console`)

## Tech Stack

- Kotlin + Spring Boot 3 (Web, Data JPA)
- H2 (in‑memory) for development
- Gradle (Kotlin DSL) • JDK 21 • Docker

## Main Endpoints

- Users (`/api/users`)
  - `GET /api/users` - List users
  - `POST /api/users` - Create user
  - `POST /api/users/login` - Login (body: `{ email, password }`)
  - `POST /api/users/logout` - Logout (body: `{ email }`)
  - `PUT /api/users/{id}` - Update user
  - `DELETE /api/users/{id}` - Delete user
- Notes (`/api/notes`)
  - `GET /api/notes?userId=` - List notes for a user
  - `POST /api/notes` - Create note
  - `PUT /api/notes/{id}` - Update note
  - `DELETE /api/notes/{id}` - Delete note
  - `DELETE /api/notes?userId=` - Delete all notes by user ID

Note: important note fields for syncing with the frontend: `timestamp`, `synced`, `isDeleted`.

## How to Run

Prerequisite: JDK 21

- Locally (Gradle):

  ```powershell
  .\gradlew.bat bootRun
  ```

  App available at http://localhost:8080

- Docker:
  ```powershell
  docker build -t keeply-backend .
  docker run -p 8080:8080 keeply-backend
  ```

## Configuration

- In‑memory H2 database (development)
  - URL: `jdbc:h2:mem:testdb`
  - User: `sa` • Password: empty
  - Console: `http://localhost:8080/h2-console` (Driver: `org.h2.Driver`)
- SQL logging enabled for debugging

## Structure (overview)

```
src/main/kotlin/com/example/demo/
  controller/  (UserController, NoteController)
  model/       (User, Note)
  repository/  (UserRepository, NoteRepository)
  service/     (NoteService)
```

## Frontend Link

Keeply Android Frontend: https://github.com/Campi98/DAM_Keeply (syncs via REST with these endpoints).
