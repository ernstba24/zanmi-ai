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
        // TODO: call the model through chatClient (developer-owned) and return the generated text.
        throw new UnsupportedOperationException("Model call not implemented yet");
    }
}
