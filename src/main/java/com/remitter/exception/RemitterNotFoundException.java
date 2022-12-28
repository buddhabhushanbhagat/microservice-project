package com.remitter.exception;

import lombok.Data;

@Data
public class RemitterNotFoundException extends RuntimeException {
	private String message;
	public RemitterNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		this.message = string;
	}

}
