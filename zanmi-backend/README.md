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

## Chat API and error responses

`POST /api/chat` accepts `{"message": "..."}` and returns `{"response": "..."}`. `message` must not be blank and may be at most 4000 characters.

All errors use one JSON shape:

```json
{
  "timestamp": "2026-11-06T14:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "code": "VALIDATION_ERROR",
  "message": "The request contains invalid fields",
  "path": "/api/chat",
  "fieldErrors": [{ "field": "message", "message": "must not be blank" }]
}
```

`fieldErrors` appears only for validation errors. Messages are fixed text; provider messages, stack traces and request contents are never returned.

| Situation | HTTP | `code` |
|---|---|---|
| Blank, missing or too-long `message` | 400 | `VALIDATION_ERROR` |
| Missing or malformed JSON body | 400 | `MALFORMED_REQUEST` |
| Other Spring web errors (for example wrong HTTP method) | their own status | `REQUEST_ERROR` |
| Provider rejected the request (bad key, quota, rate limit) | 502 | `PROVIDER_ERROR` |
| Provider unreachable or returned a 5xx error | 503 | `PROVIDER_UNAVAILABLE` |
| Provider did not answer in time | 504 | `PROVIDER_TIMEOUT` |
| Any other unexpected error | 500 | `INTERNAL_ERROR` |

Provider details are written to the server log as the reason and exception class only. No retry, fallback or timeout setting is configured by this application; Spring AI applies its own default retry behavior.

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
