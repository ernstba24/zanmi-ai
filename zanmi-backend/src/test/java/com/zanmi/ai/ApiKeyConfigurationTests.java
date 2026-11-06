package com.zanmi.ai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ConfigurableApplicationContext;

@ExtendWith(OutputCaptureExtension.class)
class ApiKeyConfigurationTests {

    @Test
    void startupFailsWithClearMessageWhenApiKeyIsEmpty() {
        assertThatThrownBy(() -> run("--spring.ai.openai.api-key="))
                .hasStackTraceContaining("spring.ai.openai.api-key");
    }

    @Test
    void startupDoesNotLogApiKeyValue(CapturedOutput output) {
        String secret = "test-secret-value-must-not-be-logged";

        try (ConfigurableApplicationContext context = run("--spring.ai.openai.api-key=" + secret)) {
            assertThat(context.isActive()).isTrue();
        }

        assertThat(output).doesNotContain(secret);
    }

    // Command-line arguments take precedence over the OPENAI_API_KEY value set for tests.
    private ConfigurableApplicationContext run(String... args) {
        return new SpringApplicationBuilder(ZanmiBackendApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
