# ZANMI AI Backend

The backend is a Java 21 and Spring Boot application built with Maven.

## Prerequisites

- JDK 21
- Maven 3.6.3 or later

Confirm your installed versions with `java -version` and `mvn -version`.

## Run locally

From this directory, set `OPENAI_API_KEY` (see [Configuration](#configuration)) and start the application with:

```shell
mvn spring-boot:run
```

The health endpoint is available at [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health). A healthy application responds with a status of `UP`.

## Configuration

Model settings are read from external configuration. Defaults that are safe to commit live in [application.yml](src/main/resources/application.yml). Secrets are never stored in the repository.

| Variable | Required | Default | Purpose |
|---|---|---|---|
| `OPENAI_API_KEY` | Yes | none | OpenAI API key (secret). |
| `OPENAI_MODEL` | No | `gpt-4o-mini` | OpenAI model name. |
| `SPRING_AI_OPENAI_CHAT_OPTIONS_TEMPERATURE` | No | unset | Sampling temperature. When unset, the OpenAI default applies. |

If `OPENAI_API_KEY` is missing or empty, the application fails at startup with an error that names the `spring.ai.openai.api-key` property. The error does not print any key value.

### Supplying values locally

Use either approach:

1. **Shell environment variables:**

   ```shell
   export OPENAI_API_KEY=your-api-key
   mvn spring-boot:run
   ```

2. **A local `.env` file** in this directory. Copy the template and replace the placeholder:

   ```shell
   cp .env.example .env
   ```

   The application imports `.env` automatically when it exists. `.env` is ignored by git; only the placeholder-only [.env.example](.env.example) is committed. Never commit real keys.

Values are resolved in this order, highest first: command-line arguments, OS environment variables, `.env`, then the defaults in `application.yml`. Tests always use a fake `OPENAI_API_KEY` supplied by Maven Surefire, so they never use a real key or call OpenAI.

Do not expose additional Actuator endpoints or enable debug logging for the HTTP client without checking that secrets cannot leak.

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
