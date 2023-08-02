package com.interview.snl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.snl.bean.Player;
import com.interview.snl.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SnlControllerIntegrationTest {

    public static final String INVALID_ID = "invalid";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddPlayer() throws Exception {
        mockMvc.perform(post("/snl/players"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.position").value(0))
                .andExpect(jsonPath("$.score").value(0));
    }

    @Test
    public void testGetPlayerStatus() throws Exception {
        String result = mockMvc.perform(post("/snl/players"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Player player = objectMapper.readValue(result, Player.class);

        mockMvc.perform(get("/snl/players/{playerId}/status", player.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(player.getId()))
                .andExpect(jsonPath("$.status", not(Status.UNRECOGNIZED.name())));

        mockMvc.perform(get("/snl/players/{playerId}/status", INVALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(INVALID_ID))
                .andExpect(jsonPath("$.status").value(Status.UNRECOGNIZED.name()));
    }
}
