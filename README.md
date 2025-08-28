# Askend Test Assignment - Filter Manager

Application which can be used to create new filters and view/alter already created filters.

---

## Tech Stack

- **Backend:** Spring Boot 3.5.4 (Gradle, Java 21)  
- **Database:** H2 (in-memory) with Liquibase for migrations  
- **Frontend:** Angular 17.x (Node.js 20.x)  
- **Deployment:** Docker Compose  

**Container Ports:**  
- Backend: `8080`  
- Frontend: `4200`  

---

## Getting Started

### Run with Docker Compose (recommended)

```bash
docker-compose up --build
```

Backend → http://localhost:8080
Frontend → http://localhost:4200

### Run Locally (without Docker)
**Backend (Spring Boot)**
```
./gradlew bootRun
```

Runs at: http://localhost:8080

**Frontend (Angular)**
```
npm install
ng serve
```

Runs at: http://localhost:4200

### Database

- Uses an H2 in-memory database (data is lost when app stops).
- Liquibase handles database migrations.

**To access H2 console:**

- **URL** → http://localhost:8080/h2-console
- **JDBC URL** → jdbc:h2:mem:testdb
- **User** → sa
- **Password** → empty unless overridden in application.properties

### Project Structure
```
backend/   # Spring Boot application
frontend/  # Angular application
docker-compose.yml
```
## Requirements
- Docker & Docker Compose

### When running locally
- Java 21
- Node.js 20+
- Angular CLI 17+
- Gradle 8+

