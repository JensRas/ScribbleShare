package edu.iastate.scribbleshare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Bad hash")  // 404
public class BadHashException extends RuntimeException {
    
}