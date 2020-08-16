package com.lovius.common.exception;

public class DeleteRollBackException extends RuntimeException {

	private static final long serialVersionUID = 6381987970248838981L;

	public DeleteRollBackException() {
		super();
	}

	public DeleteRollBackException(String message) {
		super(message);
	}

	public DeleteRollBackException(Throwable cause) {
		super(cause);
	}

	public DeleteRollBackException(String message, Throwable cause) {
		super(message, cause);
	}

}
