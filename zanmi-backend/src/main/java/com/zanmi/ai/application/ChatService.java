package com.zanmi.ai.application;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatGateway chatGateway;

    public ChatService(ChatGateway chatGateway) {
        this.chatGateway = chatGateway;
    }

    public String chat(String message) {
        // TODO: define the chat behavior (developer-owned), then call chatGateway.
        throw new UnsupportedOperationException("Chat behavior not implemented yet");
    }
}
