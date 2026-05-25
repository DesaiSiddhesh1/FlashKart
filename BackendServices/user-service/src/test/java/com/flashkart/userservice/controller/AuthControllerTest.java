package com.flashkart.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashkart.userservice.dto.UserRequestDto;
import com.flashkart.userservice.dto.UserResponseDto;
import com.flashkart.userservice.service.UserService;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUserTest() throws Exception {

        UserRequestDto request = new UserRequestDto();

        request.setName("Siddhesh");
        request.setEmail("test@test.com");
        request.setPassword("123456");
        request.setRole("ROLE_USER");

        UserResponseDto response = new UserResponseDto();

        response.setId(1L);
        response.setName("Siddhesh");
        response.setEmail("test@test.com");
        response.setRole("ROLE_USER");

        when(service.register(any(UserRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}