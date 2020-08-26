package com.lovius.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

	private static final long serialVersionUID = 4242478220010503573L;

	@Id
	private long id;
	
	private String seqNo;
	
	private int lvl;
	
	private String api;

	private String className;

	private String classMethod;

	private String inArgs;

	private String outArgs;
	
	private String sqlText;
	
	private String error;
	
	private String sysDate;
	
	private String sysTime;
	
	private String uri;

}
