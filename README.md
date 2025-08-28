# Hospital App - Common Module

This repository contains the **common module** for the [Hospital App](https://github.com/gabriel-dears/hospital_app) ecosystem. It provides shared components, utilities, and base classes that are reused across various Spring Boot microservices within the system.

---

## 📦 Overview

The `common` module includes:

- **Exception Handling**:
    - `GlobalExceptionHandlerBase`: Base class for centralized exception handling.
    - `ErrorResponse`: Standardized error payload for APIs.
    - `ErrorResponseFactory`: Creates error responses for common HTTP status codes.
    - `ErrorResponseEntityFactory`: Creates `ResponseEntity` objects containing `ErrorResponse`.

- **Database Utilities**:
    - `DbOperationWrapper`: Wraps database operations and maps exceptions to custom runtime exceptions.

These components are designed to promote consistency, reduce duplicated code, and provide centralized utilities for all Spring Boot services in the hospital system.

---

## 🧩 Integration

To integrate this module into your Spring Boot microservices:

1. Add the following dependency to your `pom.xml`:

   ```xml
   <dependency>
       <groupId>com.hospital_app</groupId>
       <artifactId>common</artifactId>
       <version>0.0.1-SNAPSHOT</version>
   </dependency>

2. Ensure that your service's pom.xml includes the parent POM:

    ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

3. In your service, extend GlobalExceptionHandlerBase to handle exceptions:

    ```java
   @RestControllerAdvice
    public class AppGlobalExceptionHandler extends GlobalExceptionHandlerBase {
    // Override base methods or add service-specific exception handling here
    }

4. Use ErrorResponseEntityFactory to return standardized error responses:

    ```java
   return ErrorResponseEntityFactory.getNotFound(new Exception("Patient not found"), request);

5. Use DbOperationWrapper for executing database operations with standardized exception handling:

    ```java
    String result = DbOperationWrapper.execute(
    () -> patientRepository.findById(patientId).orElseThrow(),
    CustomDatabaseException.class
    );

## 🛠️ Development

To build and install the module locally:

```bash
    git clone https://github.com/gabriel-dears/common.git
    cd common
    mvn clean install
```

This will install the module into your local Maven repository, making it available for other services to use.
