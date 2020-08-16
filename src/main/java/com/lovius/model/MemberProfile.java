package com.lovius.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfile implements Serializable {

	private static final long serialVersionUID = 1924112708509814902L;

	@Id
	private String id;
	
	private String name;
	
	private String nickName;

	private String sex;
	
	private int age;
	
	private String tel;
	
	private String address;
	
}
