package com.monografia.forum.services.exceptions;

public class NaoAutorizadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NaoAutorizadoException(String msg) {
		super(msg);
	}
}
