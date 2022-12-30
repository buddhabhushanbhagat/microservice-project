package com.remitter.exception;

import lombok.Data;

@Data
public class RemitterUpdateFailedException extends RuntimeException {
	private String message;
	public RemitterUpdateFailedException(String string) {
		// TODO Auto-generated constructor stub
		this.message = string;
	}
}
