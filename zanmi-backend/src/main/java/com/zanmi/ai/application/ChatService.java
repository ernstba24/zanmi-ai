package com.zanmi.ai.application;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatGateway chatGateway;

    public ChatService(ChatGateway chatGateway) {
        this.chatGateway = chatGateway;
    }

    public String chat(String message) {
        return chatGateway.generate(message);
    }
}
