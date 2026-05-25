package com.flashkart.userservice.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flashkart.userservice.entity.User;
import com.flashkart.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email)
	throws UsernameNotFoundException{
		User user = repository.findByEmail(email)
				.orElseThrow(() -> new
						UsernameNotFoundException("User not found"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
				);
		
	}
}
