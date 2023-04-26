package com.assignment.choi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DEPARTMENT_TB")
@Getter
@Setter
public class DepDto {
	@Id
	@Column(name="dep_id", length=1) // 2
	private String dep_id="";
	
	@Column(name="dep_nm", length=30)
	private String dep_nm="";   // 개발팀
	
}
