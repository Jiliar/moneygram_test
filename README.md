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
- **Additional**: Lombok, H2 Console (for development)

## Prerequisites

- Java 21 or higher
- No additional installations required (uses Gradle wrapper)

## Setup and Installation

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd ms_books/test
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
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
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0-7432-7356-5",
  "publishedYear": 1925,
  "genre": "Fiction"
}
```

## Testing the API

### Using curl

1. **Create a book**
   ```bash
   curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{
       "title": "The Great Gatsby",
       "author": "F. Scott Fitzgerald",
       "isbn": "978-0-7432-7356-5",
       "publishedYear": 1925,
       "genre": "Fiction"
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
       "title": "The Great Gatsby - Updated",
       "author": "F. Scott Fitzgerald",
       "isbn": "978-0-7432-7356-5",
       "publishedYear": 1925,
       "genre": "Classic Fiction"
     }'
   ```

5. **Delete a book**
   ```bash
   curl -X DELETE http://localhost:8080/api/books/1
   ```

### Using Postman

1. Import the following collection or create requests manually
2. Set base URL to `http://localhost:8080`
3. Use the endpoints listed above with appropriate HTTP methods

## Development

### Running Tests

```bash
./gradlew test
```

### Development Mode

The application includes Spring Boot DevTools for automatic restart during development:

```bash
./gradlew bootRun
```

### Project Structure

```
src/
├── main/
│   ├── java/com/moneygram/tecnical/test/
│   │   ├── TestApplication.java          # Main application class
│   │   ├── controller/                   # REST controllers
│   │   ├── model/                        # Book entity/model
│   │   ├── service/                      # Business logic
│   │   └── repository/                   # Data access layer
│   └── resources/
│       └── application.properties        # Configuration
└── test/
    └── java/                            # Unit tests
```

## Configuration

The application uses default Spring Boot configuration. Key settings in `application.properties`:

```properties
spring.application.name=test
server.port=8080
```

## Data Storage

This application uses in-memory data storage:
- Books are stored in a `HashMap<Long, Book>` for fast ID-based lookups
- Data is lost when the application restarts
- No external database configuration required

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful GET, PUT requests
- `201 Created` - Successful POST requests
- `204 No Content` - Successful DELETE requests
- `404 Not Found` - Book not found
- `400 Bad Request` - Invalid request data

## Future Enhancements

- Add input validation
- Implement pagination for book listings
- Add search and filtering capabilities
- Include API documentation with Swagger/OpenAPI
- Add comprehensive unit and integration tests

## License

This project is for educational/assessment purposes.