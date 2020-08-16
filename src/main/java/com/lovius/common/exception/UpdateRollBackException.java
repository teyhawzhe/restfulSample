package com.lovius.common.exception;

public class UpdateRollBackException extends RuntimeException {

	private static final long serialVersionUID = 6336288161901813415L;

	public UpdateRollBackException() {
		super("更新失敗!");
	}

	public UpdateRollBackException(String message) {
		super(message);
	}

	public UpdateRollBackException(Throwable cause) {
		super(cause);
	}

	public UpdateRollBackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
