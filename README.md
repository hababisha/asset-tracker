# IT Asset & Equipment Tracker API

Spring Boot 3 (Java 21) + PostgreSQL + JWT, containerized with Docker Compose.

## Run with Docker
    cp .env.example .env      # then edit the values
    docker compose up --build

API: http://localhost:8080   Swagger UI: http://localhost:8080/swagger-ui.html

## Run locally (without Docker)
Start PostgreSQL, then set these environment variables and run `mvn spring-boot:run`:
SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD,
JWT_SECRET, ADMIN_USERNAME, ADMIN_PASSWORD

## Endpoints
| Method | Endpoint | Roles |
|---|---|---|
| POST | /api/auth/register | public (creates ROLE_STAFF) |
| POST | /api/auth/login | public |
| GET | /api/categories | ADMIN, STAFF |
| POST | /api/categories | ADMIN |
| GET | /api/assets[?status=] | ADMIN, STAFF |
| GET | /api/assets/my-assets | ADMIN, STAFF |
| POST | /api/assets | ADMIN |
| PATCH | /api/assets/{id}/assign/{userId} | ADMIN |
| PATCH | /api/assets/{id}/return | ADMIN |
| DELETE | /api/assets/{id} | ADMIN |
