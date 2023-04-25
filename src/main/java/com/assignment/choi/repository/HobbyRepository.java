package com.assignment.choi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assignment.choi.domain.HobbyDto;

public interface HobbyRepository extends JpaRepository<HobbyDto, String>{
	
	@Query("select h from HobbyDto h order by h.h_code_id asc")
	List<HobbyDto> findAllOrderByAsc();
}
