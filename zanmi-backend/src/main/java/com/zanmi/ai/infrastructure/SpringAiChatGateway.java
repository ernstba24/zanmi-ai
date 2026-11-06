package com.zanmi.ai.infrastructure;

import com.zanmi.ai.application.ChatGateway;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class SpringAiChatGateway implements ChatGateway {

    private final ChatClient chatClient;

    public SpringAiChatGateway(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generate(String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
