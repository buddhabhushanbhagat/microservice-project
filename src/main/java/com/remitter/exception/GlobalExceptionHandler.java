package com.remitter.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handelException(NullPointerException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong, " + ex.getMessage());
	}
	
	@ExceptionHandler(DateParseException.class)
	public ResponseEntity<String> handelException(DateParseException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problems with your date." + ex.getMessage());
	}
	
	@ExceptionHandler(RemitterAlreadyExistsException.class)
	public ResponseEntity<String> handelException(RemitterAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong,"+ ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrorList = ex.getFieldErrors();
		List<String> fieldErrorStringList = new ArrayList<>();
		for(FieldError fieldError:fieldErrorList) {
			fieldErrorStringList.add(fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorStringList);
	}
	
}
