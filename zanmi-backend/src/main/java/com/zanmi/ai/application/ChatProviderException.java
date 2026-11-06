package com.zanmi.ai.application;

/**
 * Provider-neutral failure raised when the model provider cannot complete a request. The message is
 * fixed and safe; the original cause is kept for server-side logging only.
 */
public class ChatProviderException extends RuntimeException {

    public enum Reason {
        /** Provider returned a 5xx error or could not be reached. */
        UNAVAILABLE,
        /** Provider did not answer in time. */
        TIMEOUT,
        /** Provider rejected the request with a 4xx error (credentials, quota, rate limit, ...). */
        REJECTED
    }

    private final Reason reason;

    public ChatProviderException(Reason reason, Throwable cause) {
        super("Chat provider failure: " + reason, cause);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
