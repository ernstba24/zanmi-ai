package com.zanmi.ai.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatServiceTests {

    private ChatGateway chatGateway;
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatGateway = mock(ChatGateway.class);
        chatService = new ChatService(chatGateway);
    }

    @Test
    void chatReturnsGatewayResult() {
        when(chatGateway.generate("Hello")).thenReturn("Hi there");

        String result = chatService.chat("Hello");

        assertEquals("Hi there", result);
        verify(chatGateway).generate("Hello");
    }
}
