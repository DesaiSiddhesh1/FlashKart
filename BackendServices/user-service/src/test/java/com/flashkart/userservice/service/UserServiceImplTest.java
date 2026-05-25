package com.flashkart.userservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flashkart.userservice.dto.UserRequestDto;
import com.flashkart.userservice.entity.User;
import com.flashkart.userservice.exception.DuplicateResourceException;
import com.flashkart.userservice.repository.UserRepository;
import com.flashkart.userservice.security.JwtUtil;
import com.flashkart.userservice.service.impl.UserServiceImpl;

public class UserServiceImplTest {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper mapper;
    private JwtUtil jwtUtil;

    private UserServiceImpl service;

    @BeforeEach
    void setup() {

        repository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        mapper = new ModelMapper();
        jwtUtil = mock(JwtUtil.class);

        service = new UserServiceImpl(
                repository,
                passwordEncoder,
                mapper,
                jwtUtil);
    }

    @Test
    void registerUserSuccessfully() {

        UserRequestDto dto = new UserRequestDto();

        dto.setName("Siddhesh");
        dto.setEmail("test@test.com");
        dto.setPassword("123456");
        dto.setRole("ROLE_USER");

        when(repository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        User savedUser = new User(
                1L,
                dto.getName(),
                dto.getEmail(),
                "encodedPassword",
                dto.getRole());

        when(repository.save(any(User.class)))
                .thenReturn(savedUser);

        var response = service.register(dto);

        assertNotNull(response);
        assertEquals(dto.getEmail(), response.getEmail());
    }

    @Test
    void shouldThrowExceptionIfEmailExists() {

        UserRequestDto dto = new UserRequestDto();

        dto.setEmail("test@test.com");

        when(repository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                DuplicateResourceException.class,
                () -> service.register(dto));
    }
}