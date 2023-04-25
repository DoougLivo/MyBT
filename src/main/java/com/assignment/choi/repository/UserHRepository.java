package com.assignment.choi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.choi.domain.UserHDto;


public interface UserHRepository extends JpaRepository<UserHDto, String>{

	@Query("select d from UserHDto d where d.userDto.userId=:userId")
	List<UserHDto> findByHobby(@Param("userId")String userId);
	
//	void deleteByUserId(String userId);
	
	@Transactional
	void deleteByUserDto_UserId(@Param("userId")String userId);
	
//	@Query("update UserHDto d set user_id='1' where d.userDto.user_id=:userId")
//	void updateUserNull(@Param("userId")String user_id);
}
