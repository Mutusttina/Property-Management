package com.speedhome.controller;

import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;
import com.speedhome.model.LoginRequest;
import com.speedhome.model.LoginResponse;
import com.speedhome.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.speedhome.security.JwtUtil;
import com.speedhome.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;


	@PostMapping("/authenticate")
	public ResponseEntity<?> login(@RequestBody LoginRequest userInfo) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword()));
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(jwt));
	}

	@PostMapping
	public void addUser(@RequestBody AddUsersRequest user){
		userService.addUser(user);
	}



}
