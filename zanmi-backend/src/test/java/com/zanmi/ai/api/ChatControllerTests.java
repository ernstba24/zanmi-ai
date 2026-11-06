package com.zanmi.ai.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zanmi.ai.application.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

    // TODO: add tests for request validation once you define that behavior (US-005).
}
