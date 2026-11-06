# ZANMI AI Backend

The backend is a Java 21 and Spring Boot application built with Maven.

## Prerequisites

- JDK 21
- Maven 3.6.3 or later

Confirm your installed versions with `java -version` and `mvn -version`.

## Run locally

From this directory, start the application with:

```shell
mvn spring-boot:run
```

The health endpoint is available at [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health). A healthy application responds with a status of `UP`.

## Run tests

From this directory:

```shell
mvn test
```

The build fails if any test fails.

## Code style

Basic editor settings (UTF-8, LF line endings, 4-space indentation, final newline) are defined in [.editorconfig](.editorconfig). Java formatting is automated with [Spotless](https://github.com/diffplug/spotless) using google-java-format in AOSP (4-space) style.

```shell
mvn spotless:check   # report formatting violations
mvn spotless:apply   # fix formatting automatically
```

Spotless is not bound to `mvn test`, so run these commands before committing.

## Test conventions

Tests use JUnit 5 and Spring Boot Test (`spring-boot-starter-test`, which also provides AssertJ and Mockito).

- **Location:** tests live under `src/test/java` in the same package as the class they test.
- **Naming:** test classes end in `Tests`, for example `HealthEndpointTests`. Test method names describe the expected behavior.
- **Unit tests:** test one class in isolation with plain JUnit and Mockito mocks. They do not start Spring and should be preferred for domain and application logic.
- **Slice tests:** use annotations such as `@WebMvcTest` to load only one layer, for example a controller and its JSON mapping.
- **Integration tests:** use `@SpringBootTest` (optionally with `@AutoConfigureMockMvc`) to start the full application context. Use them sparingly, for wiring, configuration, and end-to-end HTTP checks, because they are slower.
- **Expected behavior:** assertions come from documented requirements. Do not write assertions for behavior that has not been defined.
