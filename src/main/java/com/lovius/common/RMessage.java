package com.lovius.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RMessage implements Serializable {

	private static final long serialVersionUID = -2000834307136506453L;

	public RMessage(int status , String message ) {
		this.status = status;
		this.message = message;
	}
	
	private int status;
	
	private String message;
	
	private Object data;
}
