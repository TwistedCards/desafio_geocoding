package com.desafio.calindra.error;

import lombok.Data;

@Data
public class DefaultError {
	private int code;
	private String message;
	
	public DefaultError() {
	}
	
	public DefaultError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
}
