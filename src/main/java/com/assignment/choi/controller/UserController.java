package com.assignment.choi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.choi.domain.DepDto;
import com.assignment.choi.domain.HobbyDto;
import com.assignment.choi.domain.UserDto;
import com.assignment.choi.domain.UserHDto;
import com.assignment.choi.domain.UserHDtoPK;
//import com.assignment.choi.service.UserService;
import com.assignment.choi.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	String goMainPg() {
		return "index";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test123";
	}
	
	// 사용자 포털
	@GetMapping("/user_PT")
	Map<String, Object> goUser() {
		System.out.println("8082로 이동됨");
		Map<String, Object> map = new HashMap<String, Object>();
		// 부서 목록
		List<DepDto> depList = userService.getDepList();
		System.out.println("dep갯수: "+depList.size());
		System.out.println("depList : "+depList);
		// 취미 목록
		List<HobbyDto> getHobbyList = userService.getHobbyList();
		System.out.println("취미목록 : "+ getHobbyList.size());
		System.out.println("getHobbyList : "+getHobbyList);
		
		map.put("depList", depList);
		map.put("getHobbyList", getHobbyList);
		
		System.out.println("Map : " + map);
		return map;
	}
	
	// 사용자 승인 요청
	@ResponseBody
	@PostMapping("/insert_user_PT")
	Map<String, String> insert_user(@RequestBody UserDto dto) {
		Map<String, String> map = new HashMap<>();
		try {
			// 사용자 추가
			userService.insert(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 사용자 취미 승인 요청
	@PostMapping("/insert_userHobby_PT")
	Map<String, String> insert_user_hobby(@RequestBody UserHDto hDto) {
		Map<String, String> map = new HashMap<>();
		try {
			System.out.println("bt 컨트롤러 hDto : "+ hDto);
			// 취미 등록
			String h_code_id = hDto.getHobbyDto().getH_code_id();
			String userId = hDto.getUserDto().getUserId();
			userService.insertHobby(h_code_id, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 관리자 포털
	@GetMapping("/admin_PT")
	Map<String, Object> getList(String searchKeyword) {
		Map<String, Object> map = new HashMap<String, Object>();
			
			if(searchKeyword != null) {
				List<UserDto> searchList = userService.searchUser(searchKeyword);
				map.put("list", searchList);
				return map;
			} else {
				List<UserDto> list = userService.getList();
				map.put("list", list);
				return map;
			}
	}
		
	@GetMapping("/depList_PT")
	Map<String, Object> depList() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 부서 목록
		List<DepDto> depList = userService.getDepList();
		map.put("depList", depList);
		return map;
	}
	
	@GetMapping("/hobbyList_PT")
	Map<String, Object> hobbyList() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 취미 목록
		List<HobbyDto> getHobbyList = userService.getHobbyList();
		map.put("getHobbyList", getHobbyList);
		return map;
	}
	
	@GetMapping("/hci_PT")
	Map<String, Object> gethci(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
//		// 취미 목록
//		List<HobbyDto> getHobbyList = userService.getHobbyList();
//		map.put("getHobbyList", getHobbyList);
		// 사용자 취미
		List<UserHDto> viewHList = userService.getHobby(userId);
//		map.put("viewHList", viewHList);
		System.out.println("취미리스트 갯수 : " + viewHList.size());
		// 나눠서 저장
		String hci="";
		for(int i=0; i < viewHList.size(); i++) {
			hci += viewHList.get(i).getHobbyDto().getH_code_id();
		} // viewHList1   viewHList2  
		map.put("hci", hci);
		System.out.println("hci : "+ hci);
		return map;
	}
	
	// 관리자 포털 상세보기
	@GetMapping("/admin/{userId}")
	Map<String, Object> getView(@PathVariable("userId")String userId, String searchKeyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 사용자 정보
		UserDto view = userService.getView(userId);
		map.put("view", view);
		System.out.println("사용자의 부서 : " + view.getDepDto().getDep_id());
		return map;
	}
	
	// 사용자 정보 수정
	@ResponseBody
	@PostMapping("/admin/update_PT")
	Map<String, String> updateUser(@RequestBody UserDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 사용자 정보 수정
			userService.updateUser(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
//	// 사용자 취미 수정
//	@ResponseBody
//	@PostMapping("/admin/updateHobby_PT")
//	Map<String, String> updateUserHobby(@RequestBody UserHDtoPK pk) {
//		Map<String, String> map = new HashMap<String, String>();
//		System.out.println("@@@@@@@@@ pk pk : "+pk);
//		System.out.println("취미코드 : " + pk.getH_code_id());
//		try {
//			if (pk.getH_code_id() == null) {
//				// 취미 삭제
//				userService.deleteHobby(pk);
//				System.out.println("취미 삭제됨1");
//			}
//			
//			// 취미 수정
//			if (pk.getH_code_id() != null) { // 취미가 있으면
//				// 취미 삭제
//				userService.deleteHobby(pk);
//				System.out.println("취미 삭제됨2");
//				
//				System.out.println("취미코드 : " + pk.getH_code_id());
//				
//				// 임시로 저장하기 위해 만듬
//				UserHDto newUHDto = new UserHDto();
//				HobbyDto newH_Dto = new HobbyDto();
//				UserDto newU_Dto = new UserDto();
//				
//				if (pk.getH_code_id().contains(",")) {
//					String[] hic = pk.getH_code_id().split(",");
//					
//					for (int i = 0; i < hic.length; i++) {
//						// 임시 변수
//						System.out.println("취미코드 : " + hic[i]);
//						pk.setH_code_id(hic[i]);
//						pk.setUserId(dto.getUserId());
//						
//						// h_code_id
//						newH_Dto.setH_code_id(pk.getH_code_id());
//						newUHDto.setHobbyDto(newH_Dto);
//						
//						// user_id
//						newU_Dto.setUserId(pk.getUserId());
//						newUHDto.setUserDto(newU_Dto);
//						
//						// 사용자 취미 수정
//						userService.insertHobby(newUHDto);
//						userService.updateUserHobby(dto.getUser_id(), newUHDto);
//					}
//				} else {
//					// 임시 변수
//					System.out.println("취미코드 : " + pk.getH_code_id());
//					pk.setH_code_id(pk.getH_code_id());
//					pk.setUserId(dto.getUserId());
//					
//					// h_code_id
//					newH_Dto.setH_code_id(pk.getH_code_id());
//					newUHDto.setHobbyDto(newH_Dto);
//					
//					// user_id
//					newU_Dto.setUserId(pk.getUserId());
//					newUHDto.setUserDto(newU_Dto);
//					
//					// 사용자 취미 수정
////						userService.insertHobby(newUHDto);
//					// userService.updateUserHobby(dto.getUser_id(), newUHDto);
//				}
//			}
//			map.put("msg", "success");
//			map.put("msg2", "저장되었습니다.");
//		} catch (Exception e) {
//			map.put("msg", "fail");
//			e.printStackTrace();
//		}
//		return map;
//	}
	
	// 사용자 삭제
	@ResponseBody
	@PostMapping("/admin/delete_PT")
	Map<String, String> deleteUser(@RequestBody UserDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("아이디 : " + dto.getUserId());
		System.out.println("부서 : " + dto.getDepDto().getDep_id());
		try {
			// 사용자 삭제
			userService.deleteUser(dto);
			System.out.println("사용자 삭제됨");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 사용자 취미 삭제
	@ResponseBody
	@PostMapping("/admin/deleteHobby_PT")
	Map<String, String> deleteUser(@RequestBody UserHDtoPK pk) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("pk : "+ pk);
		System.out.println("취미코드 : "+pk.getH_code_id());
		try {
			// 취미 삭제
			userService.deleteHobby(pk);
			System.out.println("취미 삭제됨");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
		
	// 아이디 중복체크
	@PostMapping("/idcheck_PT")
	int idCheck(@RequestBody String userId) {
		try {
			int result = userService.idCheck(userId);
			if(result == 0) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
}