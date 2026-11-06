package com.zanmi.ai.api;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zanmi.ai.application.ChatProviderException;
import com.zanmi.ai.application.ChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ChatController.class)
class ChatControllerTests {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private ChatService chatService;

    // Verifies the documented JSON contract: {"message": ...} in, {"response": ...} out.
    @Test
    void postChatReturnsResponseField() throws Exception {
        given(chatService.chat("test message")).willReturn("stubbed reply");

        mockMvc.perform(
                        post("/api/chat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"message\": \"test message\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("stubbed reply"));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "{}",
                "{\"message\": null}",
                "{\"message\": \"\"}",
                "{\"message\": \"   \"}"
            })
    void postChatRejectsMissingOrBlankMessage(String body) throws Exception {
        mockMvc.perform(post("/api/chat").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("The request contains invalid fields"))
                .andExpect(jsonPath("$.path").value("/api/chat"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.fieldErrors[0].field").value("message"));

        verifyNoInteractions(chatService);
    }

    @Test
    void postChatRejectsTooLongMessage() throws Exception {
        String body = "{\"message\": \"" + "a".repeat(4001) + "\"}";

        mockMvc.perform(post("/api/chat").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("message"));

        verifyNoInteractions(chatService);
    }

    @Test
    void postChatAcceptsMessageAtMaxLength() throws Exception {
        String message = "a".repeat(4000);
        given(chatService.chat(message)).willReturn("ok");

        mockMvc.perform(
                        post("/api/chat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"message\": \"" + message + "\"}"))
                .andExpect(status().isOk());
    }

    // The raw payload must never be echoed back in the error response.
    @Test
    void postChatRejectsMalformedJsonWithoutEchoingPayload() throws Exception {
        mockMvc.perform(
                        post("/api/chat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"message\": \"secret-marker"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MALFORMED_REQUEST"))
                .andExpect(jsonPath("$.message").value("The request body is missing or malformed"))
                .andExpect(jsonPath("$.fieldErrors").doesNotExist())
                .andExpect(content().string(not(containsString("secret-marker"))));

        verifyNoInteractions(chatService);
    }

    private ResultActions postHello() throws Exception {
        return mockMvc.perform(
                post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Hello\"}"));
    }

    @ParameterizedTest
    @CsvSource({
        "TIMEOUT,504,PROVIDER_TIMEOUT",
        "UNAVAILABLE,503,PROVIDER_UNAVAILABLE",
        "REJECTED,502,PROVIDER_ERROR"
    })
    void providerFailuresMapToControlledResponses(
            ChatProviderException.Reason reason, int status, String code) throws Exception {
        given(chatService.chat("Hello"))
                .willThrow(
                        new ChatProviderException(
                                reason, new RuntimeException("401 key_SECRET123 internal detail")));

        postHello()
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.status").value(status))
                .andExpect(jsonPath("$.code").value(code))
                .andExpect(jsonPath("$.path").value("/api/chat"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(content().string(not(containsString("SECRET123"))))
                .andExpect(content().string(not(containsString("internal detail"))))
                .andExpect(content().string(not(containsString("Exception"))));
    }

    @Test
    void unexpectedExceptionReturnsGeneric500WithoutDetails() throws Exception {
        given(chatService.chat("Hello"))
                .willThrow(new IllegalStateException("db password=hunter2 at com.zanmi.Internal"));

        postHello()
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(content().string(not(containsString("hunter2"))))
                .andExpect(content().string(not(containsString("com.zanmi.Internal"))))
                .andExpect(content().string(not(containsString("trace"))));
    }

    @Test
    void unsupportedMethodKeepsItsStatusInsteadOfBecoming500() throws Exception {
        mockMvc.perform(get("/api/chat"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code").value("REQUEST_ERROR"));
    }
}
