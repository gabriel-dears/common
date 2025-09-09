# common

Shared utilities, DTOs, and configuration used across services in the hospital_app monorepo. This module helps standardize messaging payloads, error handling, and pagination models, reducing duplication in the microservices.


## What it provides
- Messaging
  - com.hospital_app.common.message.dto.AppointmentMessage
  - RabbitMQ JSON message converter configuration (Jackson2JsonMessageConverter)
- Error handling
  - ErrorResponse payload and factories to build consistent error responses
  - Base global exception handler to extend in services
- Pagination
  - ApplicationPage<T> to expose consistent paginated responses
- DB helpers
  - DbOperationWrapper to execute repository/service operations with consistent exception wrapping


## Tech stack
- Java 21, Spring Boot 3.5 (starter-web, starter-amqp, starter-json)


## Module coordinates
- Group: com.hospital_app
- Artifact: common
- Version: 0.0.1-SNAPSHOT

Add it as a dependency in a service pom.xml:

    <dependency>
      <groupId>com.hospital_app</groupId>
      <artifactId>common</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>

This monorepo already references it in user_service, appointment_service, and appointment_history_service.


## Packages and usage

1) Messaging (RabbitMQ JSON and DTO)
- DTO: com.hospital_app.common.message.dto.AppointmentMessage
  - Serializable payload with fields: id, patientId, patientEmail, patientName, doctorId, doctorName, dateTime, status, notes, version.
  - Used by appointment_service as the event message, and by notification_service and appointment_history_service as the consumer payload.
- Config: com.hospital_app.common.message.rabbitmt.RabbitMQCommonConfig
  - Exposes a Jackson2JsonMessageConverter bean to serialize/deserialize JSON messages.
  - Services that have @ComponentScan(basePackages = "com.hospital_app") will auto-pick this configuration.

2) Error handling (standardized API errors)
- DTO: com.hospital_app.common.exception.dto.ErrorResponse
  - Record with fields: messages (Set<String>), timestamp (ISO-8601), status (int), path (String).
- Factories:
  - ErrorResponseFactory: creates ErrorResponse for 400/401/404/500 with consistent formatting.
  - ErrorResponseEntityFactory: wraps ErrorResponse into ResponseEntity with the appropriate status code.
- Base handler: com.hospital_app.common.exception.GlobalExceptionHandlerBase
  - Provides methods to handle MethodArgumentNotValidException, generic Exception, and helper methods for custom 404/400 mappings.
  - How to use:
    - Create a class in your service:

          @RestControllerAdvice
          public class GlobalExceptionHandler extends GlobalExceptionHandlerBase {
              // Optionally override/extend handlers here
          }

    - Optionally call ErrorResponseEntityFactory methods from custom @ExceptionHandler methods.

3) Pagination
- com.hospital_app.common.db.pagination.ApplicationPage<T>
  - Simple container for consistent REST responses (pageNumber, pageSize, totalPages, totalElements, isFirst, isLast, content).
  - Typical mapping from Spring Data Page<T>:

        ApplicationPage<MyDto> toApplicationPage(Page<MyEntity> page) {
            List<MyDto> content = page.getContent().stream().map(this::toDto).toList();
            return new ApplicationPage<>(
                page.getNumber(), page.getSize(), page.getTotalPages(),
                page.getTotalElements(), page.isLast(), page.isFirst(), content
            );
        }

4) DB helpers
- com.hospital_app.common.db.DbOperationWrapper
  - Execute a lambda and wrap exceptions into a provided RuntimeException type.
  - Example:

        User result = DbOperationWrapper.execute(
            () -> repository.findById(id).orElseThrow(() -> new NotFoundException("User not found")),
            MyRepositoryException.class
        );


## Building and installing locally
- Build only this module:

      mvn -f common/pom.xml clean install -DskipTests

- Or build everything from repo root:

      mvn -q -DskipTests package

The install goal publishes the JAR to your local Maven repository (~/.m2) so other modules can depend on it in local/dev builds and in Docker multi-stage builds.


## Notes and compatibility
- The RabbitMQ JSON converter bean is "opt-in" via Spring component scan. Ensure your service has @ComponentScan(basePackages = "com.hospital_app") (already set in services in this repo) so the bean is registered.
- AppointmentMessage JSON structure must remain stable across producer/consumers. Favor additive changes to fields for backward compatibility.
- ApplicationPage is a minimal DTO intentionally decoupled from Spring Data types for portability across service APIs.


## Project paths and references
- POM: common/pom.xml
- Messaging DTO: common/src/main/java/com/hospital_app/common/message/dto/AppointmentMessage.java
- RabbitMQ config: common/src/main/java/com/hospital_app/common/message/rabbitmt/RabbitMQCommonConfig.java
- Error DTO/factories/handler base:
  - common/src/main/java/com/hospital_app/common/exception/dto/ErrorResponse.java
  - common/src/main/java/com/hospital_app/common/exception/ErrorResponseFactory.java
  - common/src/main/java/com/hospital_app/common/exception/ErrorResponseEntityFactory.java
  - common/src/main/java/com/hospital_app/common/exception/GlobalExceptionHandlerBase.java
- Pagination DTO: common/src/main/java/com/hospital_app/common/db/pagination/ApplicationPage.java
- DB helper: common/src/main/java/com/hospital_app/common/db/DbOperationWrapper.java


## Troubleshooting
- JSON message conversion issues:
  - Ensure RabbitMQCommonConfig is on the classpath and component scanned; verify that the converter bean is present by checking application startup logs.
- Missing beans/classes in a service:
  - Confirm the service declares the dependency on common and uses @ComponentScan(basePackages = "com.hospital_app").
- Serialization problems with LocalDateTime in messages:
  - Make sure your services use a consistent Jackson configuration or module for Java Time (Spring Boot’s default includes it).


## License
This module is part of an educational/portfolio repository. See root-level LICENSE if available.
