package com.zanmi.ai.application;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ChatServiceTests {

    private ChatGateway chatGateway;
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatGateway = mock(ChatGateway.class);
        chatService = new ChatService(chatGateway);
    }

    @Disabled("TODO: enable once ChatService behavior is defined and implemented")
    @Test
    void chatReturnsGatewayResult() {
        // TODO: stub chatGateway.generate(...), call chatService.chat(...), and assert the
        // outcome you define. chatGateway and chatService are ready to use.
    }
}
