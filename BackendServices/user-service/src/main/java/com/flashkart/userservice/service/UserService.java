package com.flashkart.userservice.service;

import com.flashkart.userservice.dto.AuthResponseDto;
import com.flashkart.userservice.dto.LoginRequestDto;
import com.flashkart.userservice.dto.UserRequestDto;
import com.flashkart.userservice.dto.UserResponseDto;

public interface UserService {
	UserResponseDto register(UserRequestDto request);
	
	AuthResponseDto login(LoginRequestDto request);
}
