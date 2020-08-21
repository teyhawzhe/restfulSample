package com.lovius.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogPk implements Serializable {

	private static final long serialVersionUID = 240874628563306987L;

	private String seqno;

	private int level;

	private String sysDate;

	private String sysTime;
}
