package com.crudmaster.authentication.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class RegisterResponse {
    
    private HttpStatus httpStatus;

    public RegisterResponse(HttpStatus httpStatus) {
	this.httpStatus = httpStatus;
    }
    
    
}
