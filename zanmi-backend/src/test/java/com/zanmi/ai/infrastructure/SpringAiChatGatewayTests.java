package com.zanmi.ai.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zanmi.ai.application.ChatProviderException;
import com.zanmi.ai.application.ChatProviderException.Reason;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.web.client.ResourceAccessException;

class SpringAiChatGatewayTests {

    private ChatClient chatClient;
    private SpringAiChatGateway gateway;

    @BeforeEach
    void setUp() {
        chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        when(builder.build()).thenReturn(chatClient);
        gateway = new SpringAiChatGateway(builder);
    }

    private void providerThrows(RuntimeException error) {
        when(chatClient.prompt().user(anyString()).call().content()).thenThrow(error);
    }

    @Test
    void returnsGeneratedText() {
        when(chatClient.prompt().user("Hello").call().content()).thenReturn("Hi there");

        assertEquals("Hi there", gateway.generate("Hello"));
    }

    @Test
    void translatesClientErrorToRejected() {
        providerThrows(new NonTransientAiException("401 - sensitive provider detail"));

        assertReason(Reason.REJECTED);
    }

    @Test
    void translatesServerErrorToUnavailable() {
        providerThrows(new TransientAiException("503 - sensitive provider detail"));

        assertReason(Reason.UNAVAILABLE);
    }

    @Test
    void translatesConnectionFailureToUnavailable() {
        providerThrows(new ResourceAccessException("I/O error", new IOException("refused")));

        assertReason(Reason.UNAVAILABLE);
    }

    @Test
    void translatesTimeoutToTimeout() {
        providerThrows(
                new ResourceAccessException(
                        "I/O error", new SocketTimeoutException("Read timed out")));

        assertReason(Reason.TIMEOUT);
    }

    @Test
    void keepsOriginalCauseButNotItsMessage() {
        NonTransientAiException cause = new NonTransientAiException("key_ABC123 leaked");
        providerThrows(cause);

        ChatProviderException thrown =
                assertThrows(ChatProviderException.class, () -> gateway.generate("Hello"));

        assertSame(cause, thrown.getCause());
        assertEquals("Chat provider failure: REJECTED", thrown.getMessage());
    }

    @Test
    void leavesUnexpectedExceptionsUntranslated() {
        providerThrows(new IllegalStateException("boom"));

        assertThrows(IllegalStateException.class, () -> gateway.generate("Hello"));
    }

    private void assertReason(Reason expected) {
        ChatProviderException thrown =
                assertThrows(ChatProviderException.class, () -> gateway.generate("Hello"));
        assertEquals(expected, thrown.getReason());
    }
}
