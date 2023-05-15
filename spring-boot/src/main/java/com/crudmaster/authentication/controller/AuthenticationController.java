package com.crudmaster.authentication.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudmaster.authentication.dto.LoginDTO;
import com.crudmaster.authentication.entity.Role;
import com.crudmaster.authentication.entity.User;
import com.crudmaster.authentication.entity.repository.RoleRepository;
import com.crudmaster.authentication.entity.repository.UserRepository;
import com.crudmaster.authentication.response.LoginResponse;
import com.crudmaster.authentication.response.RegisterResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
 
	Authentication authentication = authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	SecurityContext context=SecurityContextHolder.getContext();
	Collection<? extends GrantedAuthority> authorities=context.getAuthentication().getAuthorities();
	return ResponseEntity.ok(new LoginResponse("authorized", HttpStatus.ACCEPTED,authorities));
    }
    
 // TODO create registerDTO, 
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody LoginDTO loginDTO){
	
	
	if(userRepo.existsByUsername(loginDTO.getUsername())) {
	    return ResponseEntity.ok(new RegisterResponse(HttpStatus.BAD_REQUEST));
	}
	
	User newUser=new User();
	newUser.setUsername(loginDTO.getUsername());
	newUser.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
	Role role=roleRepo.findByName("student").orElseThrow();
	Role role2=roleRepo.findByName("teacher").orElseThrow();
	List<Role> roles = new ArrayList<>();
	roles.add(role);
	roles.add(role2);
	newUser.setRoles(roles);
	userRepo.save(newUser);
	
	return ResponseEntity.ok(new RegisterResponse(HttpStatus.ACCEPTED));
	
	
    }

    @GetMapping("/test")
    public String test() {
	return "test";
    }

}
