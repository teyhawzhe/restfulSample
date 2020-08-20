package com.lovius.intercepts;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ParamType implements Serializable {

	private static final long serialVersionUID = -35737126969225277L;

	private Object param;
	
	private String type;
	
}
