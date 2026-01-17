# E-commerce API (Spring Boot)

A production-style RESTful e-commerce API built with Spring Boot. It demonstrates clean architecture, JWT-based authentication, caching with Redis, validation, and a layered domain-driven design. Suitable for showcasing backend engineering skills.

## Highlights
- Spring Boot 3 API with layered architecture (controller, service, repository, domain)
- JWT Authentication & Authorization
- Role-based access (e.g., admin vs user)
- Redis-based caching for performance (products/categories)
- Global exception handling and validation
- Ready for containerization and deployment

## Tech Stack
- Java 17+
- Spring Boot
  - Spring Web
  - Spring Security (JWT)
  - Spring Data JPA (Hibernate)
  - Spring Cache (Redis)
- Redis
- Maven

## Project Structure
```
src/
  main/
    java/com/zanuar/ecommerce/
      EcommerceApiApplication.java
      config/            # Security, Redis, data initialization
      controller/        # Auth, Health, Product, Order, Payment
      domain/            # Entities and base classes
      dto/               # Request/Response DTOs
      exception/         # Global handler, custom exceptions
      repository/        # Spring Data repositories
      security/          # JWT utilities and filters
      service/           # Services and implementations
    resources/
      application.yaml   # App configuration
```

## Getting Started

### Prerequisites
- Java 17+ (verify with `java -version`)
- Maven Wrapper included (use `mvnw.cmd` on Windows)
- Redis server running locally (default port 6379)

### Configuration
Check `src/main/resources/application.yaml` for ports, datasource, and Redis settings. Adjust environment variables as needed:
- `SPRING_PROFILES_ACTIVE` (optional)
- `JWT_SECRET` (required for auth)
- Database props if using an external DB

### Build & Run (Windows PowerShell)
```powershell
# Build the project
.\mvnw.cmd -q -DskipTests package

# Start the application
.\mvnw.cmd -q spring-boot:run
```
The app will run on `http://localhost:8080` by default.

### Quick Smoke Test
- Health: `GET /api/health` -> should return status OK
- Static endpoints might be available under `/` depending on templates/static configuration

## API Overview

Note: Endpoints and exact payloads can be explored via the controllers under `controller/` and DTOs under `dto/`.

- Auth
  - `POST /api/auth/login`
- Products
  - `GET /api/products`
  - `GET /api/products/{id}`
  - `POST /api/products`
- Categories
  - `GET /api/categories`
- Orders
  - `POST /api/orders`
- Payments
  - `POST /api/payments`

### Auth & Security
- JWT is required for protected endpoints via `Authorization: Bearer <token>`
- Security filter: `security/JwtAuthenticationFilter` and utilities in `security/JwtUtil`
- Configure secret via `JWT_SECRET` environment variable

### Caching
- `@EnableCaching` in `EcommerceApiApplication`
- Redis configuration in `config/RedisConfig.java`
- Product/Category reads utilize caching annotations in services for performance

### Error Handling
- Global exception handling via `exception/GlobalExceptionHandler`
- Custom exceptions like `BadRequestException`, `ResourceNotFoundException`

## Development Notes

### Data Initialization
- `config/DataInitializer.java` seeds minimal data in dev/local profiles (if configured).

### Testing
- Unit/integration tests under `src/test/java`
- Run: `mvnw.cmd test`

### Environment Variables
Set in your shell or IDE run configuration:
- `JWT_SECRET=change_me_strong_secret`
- `SPRING_PROFILES_ACTIVE=dev`
- Database credentials if using external DB

## Docker (Optional)
Basic idea if you add a Dockerfile:
- Build jar: `mvnw.cmd -DskipTests package`
- Use a multi-stage Dockerfile for smaller images
- Run Redis alongside via `docker-compose`

## Roadmap
- Add OpenAPI/Swagger documentation
- Payment provider integration (Stripe/PayPal)
- Inventory management and stock reservations
- Pagination and sorting for listings
- E2E tests and testcontainers
- CI pipeline (GitHub Actions)

## How to Use This as a Portfolio
- Highlight the architecture and clean separation of concerns
- Show JWT-secured endpoints, role-based access, and caching
- Point to specific files:
  - `SecurityConfig.java` for auth setup
  - `JwtAuthenticationFilter.java` and `JwtUtil.java` for token handling
  - `ProductService.java`, `CategoryRepository.java` for caching + data access
  - `GlobalExceptionHandler.java` for robust error handling
- Run a local demo and attach screenshots or a short video

## License
MIT License. See `LICENSE`.
