package com.assignment.choi.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access=AccessLevel.PUBLIC)
@AllArgsConstructor
//@Entity
//@Table(name="HOBBY_TB")
@Getter
@Setter
public class HobbyDto {
//	@Id
//	@Column(name="h_code_id")
	private String h_code_id; // A
	
//	@Column(name="h_nm", length=40)
	private String hNm;   // 등산
	
}
