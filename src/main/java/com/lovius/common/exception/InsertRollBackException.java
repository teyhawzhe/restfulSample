package com.lovius.common.exception;

public class InsertRollBackException extends RuntimeException {

	private static final long serialVersionUID = 4957007602090021577L;

	public InsertRollBackException() {
		super();
	}

	public InsertRollBackException(String message) {
		super(message);
	}

	public InsertRollBackException(Throwable cause) {
		super(cause);
	}

	public InsertRollBackException(String message, Throwable cause) {
		super(message, cause);
	}

}
