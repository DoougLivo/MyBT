package com.assignment.choi.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHDtoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@ManyToOne(targetEntity=HobbyDto.class, fetch=FetchType.LAZY)
//	@JoinColumn(name="h_code_id")
	private String hobbyDto; // A
	
//	@Id
//	@ManyToOne(targetEntity=UserDto.class, fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private String userDto; // test
	
	@Transient
	private String userId;
	
	@Transient
	private String h_code_id;
}