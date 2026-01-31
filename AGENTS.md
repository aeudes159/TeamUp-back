# AGENTS.md - TeamUp Backend

Guidelines for AI agents working on this Kotlin/Spring Boot 4 backend.

## Project Overview

- **Kotlin 2.2.21** with **Spring Boot 4.0.2**, **Java 21**, **Gradle 9.3**
- **PostgreSQL** with Flyway migrations, **JPA/Hibernate** for persistence

## Build & Run Commands

```bash
./gradlew build                    # Build the project
./gradlew bootRun                  # Run the application
./gradlew test                     # Run all tests
./gradlew test --tests "main.java.io.takima.teamupback.TeamUpBackApplicationTests"  # Single test class
./gradlew test --tests "*.TeamUpBackApplicationTests.contextLoads"                   # Single test method
./gradlew test --tests "*UserService*"                                               # Pattern match
./gradlew clean build              # Clean build
./gradlew bootJar -x test          # Build JAR without tests
```

## Project Structure

```
src/main/kotlin/main/java/io/takima/teamupback/
├── TeamUpBackApplication.kt          # Main entry point
├── HealthCheck.kt                    # Health endpoint
├── common/exception/                 # Global exception handling
└── <domain>/                         # Feature modules (user/, group/, etc.)
    ├── <Entity>.kt                   # JPA Entity
    ├── <Entity>Controller.kt         # REST Controller
    ├── <Entity>Service.kt            # Business logic
    ├── <Entity>Repository.kt         # Spring Data JPA
    ├── <Entity>Dao.kt                # Custom queries (EntityManager)
    └── <Entity>Dto.kt                # Request/Response DTOs
src/main/resources/
├── application.yml                   # Configuration
└── migrations/                       # Flyway SQL migrations (V{major}_{minor}__desc.sql)
```

## Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Classes | PascalCase | `UserService`, `GroupController` |
| Functions/Variables | camelCase | `findById`, `userRepository` |
| Constants | SCREAMING_SNAKE_CASE | `MAX_RETRY_COUNT` |
| Database tables/columns | snake_case | `group_member`, `first_name` |

## Code Style Guidelines

### Imports
- Wildcard for Spring annotations: `import org.springframework.web.bind.annotation.*`
- Explicit for JPA: `import jakarta.persistence.*`

### Entity Classes
```kotlin
@Entity
@Table(name = "\"entity_name\"")  // Quote reserved words: "user", "group"
class EntityName(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_seq")
    @SequenceGenerator(name = "entity_id_seq", sequenceName = "entity_id_seq", allocationSize = 1)
    val id: Int? = null,
    @Column(name = "column_name", nullable = false) var propertyName: String,
    @Column(name = "created_at") val createdAt: LocalDateTime? = LocalDateTime.now()
)
```
- `Int?` for PKs, `var` for mutable, `val` for immutable, snake_case in `@Column(name = "...")`

### DTOs
```kotlin
data class EntityCreateRequest(val fieldName: String? = null)  // Nullable with defaults

data class EntityResponse(val id: Int?, val fieldName: String?) {
    companion object {
        fun fromEntity(entity: Entity) = EntityResponse(id = entity.id, fieldName = entity.fieldName)
    }
}

data class EntityListResponse(val data: List<EntityResponse>, val total: Long, val page: Int, val size: Int)
```

### Controllers
```kotlin
@RestController
@RequestMapping("/api/entities")
class EntityController(private val entityService: EntityService) {
    @GetMapping
    fun findAll(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "20") size: Int) =
        ResponseEntity.ok(entityService.findAll(page, size))
    
    @PostMapping
    fun create(@RequestBody request: EntityCreateRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(entityService.create(request))
}
```
- Constructor injection, return `ResponseEntity<T>`, use `HttpStatus.CREATED` for POST

### Services
```kotlin
@Service
@Transactional
class EntityService(private val entityRepository: EntityRepository, private val entityDao: EntityDao) {
    fun findById(id: Int): EntityResponse {
        val entity = entityRepository.findById(id).orElseThrow { ResourceNotFoundException("Entity", "id", id) }
        return EntityResponse.fromEntity(entity)
    }
}
```
- Use repository for CRUD, Dao for custom queries
- Nullable updates: `request.field?.let { entity.field = it }`

### Error Handling
```kotlin
throw ResourceNotFoundException("User", "id", userId)  // 404 Not Found
throw BadRequestException("Invalid email format")       // 400 Bad Request
```
The `GlobalExceptionHandler` in `common/exception/` handles these automatically.

### Database Migrations
- Location: `src/main/resources/migrations/`
- Naming: `V{major}_{minor}__description.sql` (e.g., `V1_0__create_primary_table.sql`)
- Use PostgreSQL sequences, quote reserved words: `"user"`, `"group"`

## Testing

- Location: `src/test/kotlin/`, naming: `<ClassName>Tests.kt`
- Use `@SpringBootTest` for integration tests, JUnit 5 (`org.junit.jupiter.api.Test`)

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:54322/postgres` | DB URL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | DB user |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | DB password |
| `PORT` | `8080` | Server port |

## Docker

```bash
docker build -t teamup-back .
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db teamup-back
```

## Common Patterns

1. **Pagination**: `page` (0-indexed) + `size`, calculate `offset = page * size`
2. **Entity references**: `@ManyToOne(fetch = FetchType.LAZY)`
3. **Composite keys**: `@EmbeddedId` with `@Embeddable` data class
