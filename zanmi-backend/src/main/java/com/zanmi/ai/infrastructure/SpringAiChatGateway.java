package com.zanmi.ai.infrastructure;

import com.zanmi.ai.application.ChatGateway;
import com.zanmi.ai.application.ChatProviderException;
import com.zanmi.ai.application.ChatProviderException.Reason;
import java.net.SocketTimeoutException;
import java.net.http.HttpTimeoutException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

@Component
public class SpringAiChatGateway implements ChatGateway {

    private final ChatClient chatClient;

    public SpringAiChatGateway(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generate(String message) {
        try {
            return chatClient.prompt().user(message).call().content();
        } catch (NonTransientAiException e) {
            throw new ChatProviderException(Reason.REJECTED, e);
        } catch (TransientAiException e) {
            throw new ChatProviderException(Reason.UNAVAILABLE, e);
        } catch (ResourceAccessException e) {
            throw new ChatProviderException(isTimeout(e) ? Reason.TIMEOUT : Reason.UNAVAILABLE, e);
        }
    }

    private static boolean isTimeout(Throwable error) {
        for (Throwable t = error; t != null; t = t.getCause()) {
            if (t instanceof SocketTimeoutException || t instanceof HttpTimeoutException) {
                return true;
            }
        }
        return false;
    }
}
