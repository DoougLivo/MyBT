package com.assignment.choi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.choi.domain.DepDto;

public interface DepRepository extends JpaRepository<DepDto, String>{
	
}
