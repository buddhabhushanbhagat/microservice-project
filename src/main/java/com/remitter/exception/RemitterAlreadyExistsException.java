package com.remitter.exception;

import lombok.Data;

@Data
public class RemitterAlreadyExistsException extends RuntimeException {
	private String message;
	public RemitterAlreadyExistsException(String string) {
		// TODO Auto-generated constructor stub
		this.message = string;
	}

}
