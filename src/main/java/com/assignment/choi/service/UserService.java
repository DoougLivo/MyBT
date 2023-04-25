package com.assignment.choi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.choi.domain.DepDto;
import com.assignment.choi.domain.HobbyDto;
import com.assignment.choi.domain.UserDto;
import com.assignment.choi.domain.UserHDto;
import com.assignment.choi.domain.UserHDtoPK;
import com.assignment.choi.repository.HobbyRepository;
import com.assignment.choi.repository.UserHRepository;
import com.assignment.choi.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userDao;
	
	@Autowired
	UserHRepository userHDao;
	
	@Autowired
	HobbyRepository hobbyDao;
	
	public void insert(UserDto dto) {
		userDao.save(dto);
	}
	
	public List<UserDto> getList() {
//		return userDao.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "signYn")));
		return userDao.findAll();
	}
	
	public UserDto getView(String userId) {
		UserDto dto = userDao.findByUserDto(userId);
		System.out.println("dto : "+dto);
		return dto;
	}
	
	public List<UserHDto> getHobby(String userId) {
		List<UserHDto> list = userHDao.findByHobby(userId);
//		System.out.println(userHDao.findByHobby(userId));  // 두번 이상 되면 에러
		System.out.println("toString : "+ list);
		return list;
	}
	
	public List<HobbyDto> getHobbyList() {
		return hobbyDao.findAllOrderByAsc();
	}
	
	public void insertHobby(UserHDto hDto) {
		userHDao.save(hDto);
	}
	
	public void deleteHobby(UserHDtoPK pk) {
		userHDao.deleteByUserDto_UserId(pk.getUserId());
	}

	public void updateUser(UserDto dto) {
		userDao.save(dto);
	}
	
	public void deleteUser(UserDto dto) {
		userDao.deleteById(dto.getUserId());
	}
	
	public List<UserDto> searchUser(String searchKeyword) {
//		return userDao.findByUserNmContaining(searchKeyword, PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "signYn")));
		return userDao.findByUserNmContaining(searchKeyword);
	}
	
	public List<DepDto> getDepList() {
		return userDao.findByDto();
	}
	
	public int idCheck(String userId) {
		int result = userDao.findByUserId(userId);
		if(result == 0) {
			return 0; //사용가능
		} else {
			return result; //사용불가능
		}
	}
}
