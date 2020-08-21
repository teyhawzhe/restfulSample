package com.lovius.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

	private static final long serialVersionUID = 4242478220010503573L;

	@EmbeddedId
	private SysLogPk sysLogPk;
	
	private String api;

	private String className;

	private String classFunction;

	private String inArgs;

	private String outArgs;

}
