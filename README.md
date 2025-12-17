# Book Catalog REST API

A simple REST API for managing a book catalog built with Java and Spring Boot. This application demonstrates essential CRUD operations with in-memory data storage.

## Features

- **Create**: Add new books to the catalog
- **Read**: Retrieve single or multiple books
- **Update**: Modify existing book details
- **Delete**: Remove books from the catalog
- In-memory data storage (no external database required)

## Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 4.0.0
- **Build Tool**: Gradle
- **Data Storage**: In-memory (HashMap/List)
- **Testing**: JUnit 5, Mockito
- **Additional**: Lombok, MapStruct, H2 Console (for development)

## Prerequisites

- Java 21 or higher
- No additional installations required (uses Gradle wrapper)

## Setup and Installation

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd moneygram_test
   ```

2. **Build the project**
   ```bash
   bash gradlew build
   ```

3. **Run the application**
   ```bash
   bash gradlew bootRun
   ```

   The application will start on `http://localhost:8080`

## API Endpoints

### Books Management

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/books` | Get all books | - |
| GET | `/api/books/{id}` | Get book by ID | - |
| POST | `/api/books` | Create new book | Book JSON |
| PUT | `/api/books/{id}` | Update existing book | Book JSON |
| DELETE | `/api/books/{id}` | Delete book | - |

### Book Model

```json
{
  "id": 1,
  "title": "Don Quixote",
  "author": "Miguel de Cervantes",
  "isbn": "978-84-376-0494-7",
  "publishedYear": 1605,
  "genre": "Novel"
}
```

## Testing the API

### Using the Test Script

Run the automated integration test script:

```bash
bash test-api.sh
```

This script will test all CRUD operations and validate HTTP status codes.

### Using curl

1. **Create a book**
   ```bash
   curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Don Quixote",
       "author": "Miguel de Cervantes",
       "isbn": "978-84-376-0494-7",
       "publishedYear": 1605,
       "genre": "Novel"
     }'
   ```

2. **Get all books**
   ```bash
   curl http://localhost:8080/api/books
   ```

3. **Get a specific book**
   ```bash
   curl http://localhost:8080/api/books/1
   ```

4. **Update a book**
   ```bash
   curl -X PUT http://localhost:8080/api/books/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Don Quixote de la Mancha",
       "author": "Miguel de Cervantes Saavedra",
       "isbn": "978-84-376-0494-7",
       "publishedYear": 1605,
       "genre": "Classic Novel"
     }'
   ```

5. **Delete a book**
   ```bash
   curl -X DELETE http://localhost:8080/api/books/1
   ```

## Development

### Running Unit Tests

```bash
bash gradlew test
```

View test reports:
```bash
open build/reports/tests/test/index.html
```

### Running Specific Tests

```bash
bash gradlew test --tests BookControllerTest
bash gradlew test --tests BookServiceTest
bash gradlew test --tests BookRepositoryTest
```

### Development Mode

The application includes Spring Boot DevTools for automatic restart during development:

```bash
bash gradlew bootRun
```

### Project Structure

```
src/
├── main/
│   ├── java/com/moneygram/tecnical/test/
│   │   ├── TestApplication.java          # Main application class
│   │   ├── controller/                   # REST controllers
│   │   │   └── BookController.java
│   │   ├── model/                        # Book entity/model
│   │   │   └── Book.java
│   │   ├── dto/                          # Data transfer objects
│   │   │   └── BookRequest.java
│   │   ├── service/                      # Business logic
│   │   │   └── BookService.java
│   │   ├── repository/                   # Data access layer
│   │   │   └── BookRepository.java
│   │   ├── mapper/                       # MapStruct mappers
│   │   │   └── BookMapper.java
│   │   └── exception/                    # Exception handling
│   │       ├── GlobalExceptionHandler.java
│   │       └── BookNotFoundException.java
│   └── resources/
│       └── application.properties        # Configuration
└── test/
    └── java/                            # Unit tests
        ├── controller/
        │   └── BookControllerTest.java
        ├── service/
        │   └── BookServiceTest.java
        └── repository/
            └── BookRepositoryTest.java
```

## Configuration

The application uses default Spring Boot configuration. Key settings in `application.properties`:

```properties
spring.application.name=test
server.port=8080
```

## Data Storage

This application uses in-memory data storage:
- Books are stored in a `ConcurrentHashMap<Long, Book>` for fast ID-based lookups
- Data is lost when the application restarts
- No external database configuration required

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful GET, PUT requests
- `201 Created` - Successful POST requests
- `204 No Content` - Successful DELETE requests
- `404 Not Found` - Book not found
- `400 Bad Request` - Invalid request data

Error handling is centralized in `GlobalExceptionHandler` using `@ControllerAdvice`.

## Architecture

The application follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Manages data access
- **Exception Layer**: Centralized error handling

## Testing

The project includes comprehensive unit tests:
- **BookControllerTest**: Tests REST endpoints with Mockito
- **BookServiceTest**: Tests business logic with mocked dependencies
- **BookRepositoryTest**: Tests in-memory data operations

All tests use JUnit 5 and Mockito for mocking.

## License

This project is for educational/assessment purposes.
