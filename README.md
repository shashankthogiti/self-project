# Shashank Project

A Spring Boot application with PostgreSQL, JOOQ, and Swagger UI.

## Prerequisites

- Java 21
- Docker and Docker Compose
- Gradle (or use the included Gradle wrapper)

## Project Structure

```
my-project/
├── src/
│   ├── main/
│   │   ├── java/com/shashank/project/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── common/          # Common utilities and exceptions
│   │   │   ├── user/            # User module (Controller, Service, Repository, DTOs)
│   │   │   └── ShashankProjectApplication.java
│   │   └── resources/
│   │       ├── db/init.sql      # Database initialization script
│   │       └── application*.properties
│   └── generated-db-entities/   # JOOQ generated classes (after running jooqGen)
├── build.gradle
├── docker-compose.yml
├── Dockerfile
└── README.md
```

## Quick Start

### 1. Start the Database

```bash
docker-compose up -d shashank-db
```

Wait for the database to be healthy (about 10-15 seconds).

### 2. Generate JOOQ Classes

```bash
./gradlew jooqGen
```

This connects to the running PostgreSQL container and generates Java classes from the database schema.

### 3. Run the Application

**Option A: Run with Gradle (Local Development)**
```bash
./gradlew bootRun -Dspring.profiles.active=local
```

**Option B: Run with Docker Compose (Full Stack)**
```bash
# Build the application JAR first
./gradlew bootJar

# Start both database and application
docker-compose up --build
```

### 4. Access the Application

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/users/all` | Get all users (paginated) |
| GET | `/api/v1/users/{id}` | Get user by ID |
| GET | `/api/v1/users/email/{email}` | Get user by email |
| POST | `/api/v1/users` | Create new user |
| PUT | `/api/v1/users/{id}` | Update user |
| DELETE | `/api/v1/users/{id}` | Delete user |
| PATCH | `/api/v1/users/{id}/activate` | Activate user |
| PATCH | `/api/v1/users/{id}/deactivate` | Deactivate user |

## Database

- **Host**: localhost
- **Port**: 35434
- **Database**: shashankdb
- **Username**: shashank
- **Password**: shashank

### User Table Schema

| Column | Type | Description |
|--------|------|-------------|
| id | UUID | Primary key |
| name | VARCHAR(255) | Full name |
| email | VARCHAR(255) | Email (unique) |
| password | VARCHAR(255) | BCrypt hashed password |
| mobile_number | VARCHAR(20) | Phone number |
| is_active | BOOLEAN | Account status |
| created_on | TIMESTAMP | Creation timestamp |
| updated_on | TIMESTAMP | Last update timestamp |
| created_by | UUID | Creator reference |
| updated_by | UUID | Last updater reference |

## Configuration

The application uses different profiles:

- `local` - For local development (connects to localhost:35432)
- `docker` - For Docker deployment (connects to shashank-db:5432)

## Useful Commands

```bash
# Start only the database
docker-compose up -d shashank-db

# Stop all containers
docker-compose down

# View logs
docker-compose logs -f

# Rebuild and restart
docker-compose up --build -d

# Generate JOOQ classes
./gradlew jooqGen

# Build JAR
./gradlew bootJar

# Run tests
./gradlew test
```

## Sample User

A sample admin user is created on database initialization:

- **Email**: admin@shashank.com
- **Password**: admin123

