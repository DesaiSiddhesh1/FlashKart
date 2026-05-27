package com.flashkart.userservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flashkart.userservice.dto.AuthResponseDto;
import com.flashkart.userservice.dto.LoginRequestDto;
import com.flashkart.userservice.dto.UserRequestDto;
import com.flashkart.userservice.dto.UserResponseDto;
import com.flashkart.userservice.entity.User;
import com.flashkart.userservice.exception.DuplicateResourceException;
import com.flashkart.userservice.exception.ResourceNotFoundException;
import com.flashkart.userservice.kafka.KafkaProducerService;
import com.flashkart.userservice.repository.UserRepository;
import com.flashkart.userservice.security.JwtUtil;
import com.flashkart.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	private final JwtUtil jwtUtil;
	private final KafkaProducerService producerService;
	
	@Override
	public UserResponseDto register(UserRequestDto request) {
		repository.findByEmail(request.getEmail())
			.ifPresent(user ->{
				throw new DuplicateResourceException(
						"Email Already Exists");
			});
		User user = mapper.map(request, User.class);
				user.setPassword(passwordEncoder.encode(request.getPassword()));
				User savedUser = repository.save(user);
				
				
				//Kafka Message
				producerService.sendMessage("user-topic",
						"New user registered : " + savedUser.getEmail());
				
				return mapper.map(savedUser,UserResponseDto.class);
		}
	
	  @Override
	    public AuthResponseDto login(LoginRequestDto request) {

	        User user = repository.findByEmail(request.getEmail())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Invalid credentials"));

	        boolean matches = passwordEncoder.matches(
	                request.getPassword(),
	                user.getPassword());

	        if (!matches) {
	            throw new ResourceNotFoundException("Invalid credentials");
	        }

	        String token = jwtUtil.generateToken(user.getEmail());

	        return new AuthResponseDto(token);
	    }

}
