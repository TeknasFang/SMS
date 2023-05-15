package com.crudmaster.authentication.response;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class LoginResponse {
    
    private String state;
    private HttpStatus httpStatus;
    private Collection<? extends GrantedAuthority> authorities;
    
    public LoginResponse(String state, HttpStatus httpStatus) {
	this.state = state;
	this.httpStatus = httpStatus;
    }

    public LoginResponse(String state, HttpStatus httpStatus, Collection<? extends GrantedAuthority> authorities) {
	this.state = state;
	this.httpStatus = httpStatus;
	this.authorities = authorities;
    }
    
    
    

}
