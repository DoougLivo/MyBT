package com.assignment.choi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
	Map<String, Object> goUser(Model model) {
		System.out.println("8082로 이동됨");
		Map<String, Object> map = new HashMap<String, Object>();
		// 부서 목록
		List<DepDto> depList = userService.getDepList();
		model.addAttribute("depList", depList);
		System.out.println("dep갯수: "+depList.size());
		System.out.println("depList : "+depList);
		// 취미 목록
		List<HobbyDto> getHobbyList = userService.getHobbyList();
		model.addAttribute("getHobbyList", getHobbyList);
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
//		System.out.println("취미코드 : "+ h_code_id);
		try {
			// 사용자 추가
			userService.insert(dto);
			
//			map.put("msg", "success");
//			map.put("ok", "승인 요청 되었습니다.");
		} catch (Exception e) {
//			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
	
	// 사용자 취미 승인 요청
	@ResponseBody
	@PostMapping("/insert_userHobby_PT")
	Map<String, String> insert_user_hobby(@RequestBody UserHDto hDto) {
		Map<String, String> map = new HashMap<>();
		System.out.println("취미코드 : "+ hDto.getH_code_id());
		try {
			// 취미 등록
			if(hDto.getH_code_id() != null) {
				// 임시로 저장하기 위해 만듬
				UserHDto newUHDto = new UserHDto();
				HobbyDto newH_Dto = new HobbyDto();
				UserDto newU_Dto = new UserDto();
				System.out.println("전체 취미코드 : " + hDto.getH_code_id());
				if(hDto.getH_code_id().contains(",")) {
					String[] hic = hDto.getH_code_id().split(",");
					
					for(int i=0; i<hic.length; i++) {
						// 임시 변수
						System.out.println("취미코드"+ (i+1) +": "+hic[i]);
						hDto.setH_code_id(hic[i]);
						hDto.setUserId(hDto.getUserId());
						
						// h_code_id
						newH_Dto.setH_code_id(hDto.getH_code_id());
						newUHDto.setHobbyDto(newH_Dto);
						
						// user_id
						newU_Dto.setUserId(hDto.getUserId());
						newUHDto.setUserDto(newU_Dto);
						
						// 사용자 취미 추가
						userService.insertHobby(newUHDto);
					}
				} else {
					// 임시 변수
					System.out.println(hDto.getH_code_id());
					hDto.setH_code_id(hDto.getH_code_id());
					hDto.setUserId(hDto.getUserId());
					
					// h_code_id
					newH_Dto.setH_code_id(hDto.getH_code_id());
					newUHDto.setHobbyDto(newH_Dto);
					
					// user_id
					newU_Dto.setUserId(hDto.getUserId());
					newUHDto.setUserDto(newU_Dto);
					
					// 사용자 취미 추가
					userService.insertHobby(newUHDto);
				}
			}
			
//			map.put("msg", "success");
//			map.put("ok", "승인 요청 되었습니다.");
		} catch (Exception e) {
//			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
	
	// 관리자 포털
	@GetMapping("/admin")
	Map<String, Object> getList(Model model, @RequestBody String searchKeyword, @RequestBody String userId/*, @RequestParam(required = false, defaultValue = "0", value = "page") int page*/) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 검색 안했을 때
		if(searchKeyword == null) {
			// 목록
			List<UserDto> list = userService.getList();
			model.addAttribute("list", list);
			map.put("list", list);
			
			// 부서 목록
			List<DepDto> depList = userService.getDepList();
			model.addAttribute("depList", depList);
			map.put("depList", depList);
			// 취미 목록
			List<HobbyDto> getHobbyList = userService.getHobbyList();
			model.addAttribute("getHobbyList", getHobbyList);
			map.put("getHobbyList", getHobbyList);
			System.out.println("취미목록 : "+ getHobbyList.size());
			// 사용자 취미
			List<UserHDto> viewHList = userService.getHobby(userId);
			map.put("viewHList", viewHList);
			System.out.println("취미리스트 갯수 : " + viewHList.size());
			// 나눠서 저장
			String hci="";
			for(int i=0; i < viewHList.size(); i++) {
				hci += viewHList.get(i).getHobbyDto().getH_code_id();
			} // viewHList1   viewHList2  
			model.addAttribute("hci", hci);
			map.put("hci", hci);
			System.out.println("hci : "+ hci);
		} else {  //검색 했을 때
			String searchKeyword_no_s = searchKeyword.replaceAll("\\s", ""); // 공백 제거
			// 검색 목록
			List<UserDto> searchList = userService.searchUser(searchKeyword_no_s);
			model.addAttribute("searchKeyword", searchKeyword_no_s);
			map.put("searchKeyword", searchKeyword_no_s);
			System.out.println("키워드명 : "+ searchKeyword_no_s);
			System.out.println("검색된 목록 갯수 : " + searchList.size());
			
			// 사용자 정보
			if(userId != "") {  //상세정보 눌렀을 때
				// 사용자 부서
				UserDto view = userService.getView(userId);
				model.addAttribute("view", view);
				map.put("view", view);
				System.out.println("부서 : " + view.getDepDto().getDep_id());
				// 부서 목록
				List<DepDto> depList = userService.getDepList();
				model.addAttribute("depList", depList);
				map.put("depList", depList);
				// 취미 목록
				List<HobbyDto> getHobbyList = userService.getHobbyList();
				model.addAttribute("getHobbyList", getHobbyList);
				map.put("getHobbyList", getHobbyList);
				System.out.println("취미목록 : "+ getHobbyList.size());
				// 사용자 취미
				List<UserHDto> viewHList = userService.getHobby(userId);
				map.put("viewHList", viewHList);
				System.out.println("취미리스트 갯수 : " + viewHList.size());
				// 나눠서 저장
				String hci="";
				for(int i=0; i < viewHList.size(); i++) {
					hci += viewHList.get(i).getHobbyDto().getH_code_id();
				} // viewHList1   viewHList2  
				model.addAttribute("hci", hci);
				map.put("hci", hci);
				System.out.println("hci : "+ hci);
			}
		}

		return map;
	}
	
	// 관리자 포털 상세보기
	@GetMapping("/admin/{userId}")
	Map<String, Object> getView(Model model, @RequestBody @PathVariable("userId")String userId, @RequestBody String searchKeyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 목록
		List<UserDto> list = userService.getList();
		model.addAttribute("list", list);
		map.put("list", list);
		
		// 사용자 정보
		UserDto view = userService.getView(userId);
		model.addAttribute("view", view);
		map.put("view", view);
		System.out.println("부서 : " + view.getDepDto().getDep_id());
		// 취미 목록
		List<HobbyDto> getHobbyList = userService.getHobbyList();
		model.addAttribute("getHobbyList", getHobbyList);
		map.put("getHobbyList", getHobbyList);
		System.out.println("취미목록 : "+ getHobbyList.size());
		// 사용자 취미
		List<UserHDto> viewHList = userService.getHobby(userId);
		System.out.println("취미리스트 갯수 : " + viewHList.size());
		// 나눠서 저장, 상세정보에 취미 불러올때 필요함
		String hci="";
		for(int i=0; i < viewHList.size(); i++) {
			hci += viewHList.get(i).getHobbyDto().getH_code_id();
		} // viewHList1   viewHList2  
		model.addAttribute("hci", hci);
		map.put("hci", hci);
		System.out.println("hci : "+ hci);
		
		// 부서 목록
		List<DepDto> depList = userService.getDepList();
		model.addAttribute("depList", depList);
		map.put("depList", depList);
		
		return map;
	}
	
	// 사용자 정보 수정
		@ResponseBody
		@PostMapping("/admin/update")
		Map<String, String> updateUser(@RequestBody UserDto dto, @RequestBody UserHDto hDto, @RequestBody String h_code_id, @RequestBody UserHDtoPK pk) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				// 사용자 정보 수정
				userService.updateUser(dto);
				
				if(h_code_id == null) {
					// 취미 삭제
					userService.deleteHobby(pk);
					System.out.println("취미 삭제됨1");
				}
				
				// 취미 수정
				if (h_code_id != null) {  // 취미가 있으면
					// 취미 삭제
					userService.deleteHobby(pk);
					System.out.println("취미 삭제됨2");
					
					System.out.println("취미코드 : "+h_code_id);
					System.out.println("hDto : "+hDto);
					
					// 임시로 저장하기 위해 만듬
					UserHDto newUHDto = new UserHDto();
					HobbyDto newH_Dto = new HobbyDto();
					UserDto newU_Dto = new UserDto();

					if (h_code_id.contains(",")) {
						String[] hic = h_code_id.split(",");

						for (int i = 0; i < hic.length; i++) {
							// 임시 변수
							System.out.println("취미코드 : " + hic[i]);
							hDto.setH_code_id(hic[i]);
							hDto.setUserId(dto.getUserId());

							// h_code_id
							newH_Dto.setH_code_id(hDto.getH_code_id());
							newUHDto.setHobbyDto(newH_Dto);

							// user_id
							newU_Dto.setUserId(hDto.getUserId());
							newUHDto.setUserDto(newU_Dto);

							// 사용자 취미 수정
							userService.insertHobby(newUHDto);
							//userService.updateUserHobby(dto.getUser_id(), newUHDto);
						}
					} else {
						// 임시 변수
						System.out.println("취미코드 : " + h_code_id);
						hDto.setH_code_id(h_code_id);
						hDto.setUserId(dto.getUserId());

						// h_code_id
						newH_Dto.setH_code_id(hDto.getH_code_id());
						newUHDto.setHobbyDto(newH_Dto);
						
						// user_id
						newU_Dto.setUserId(hDto.getUserId());
						newUHDto.setUserDto(newU_Dto);

						// 사용자 취미 수정
						userService.insertHobby(newUHDto);
						//userService.updateUserHobby(dto.getUser_id(), newUHDto);
					}
				}
				
				map.put("msg", "success");
				map.put("msg2", "저장되었습니다.");
			} catch (Exception e) {
				map.put("msg", "fail");
				e.printStackTrace();
			}
			return map;
		}
	
	// 사용자 삭제
	@ResponseBody
	@PostMapping("/admin/delete")
	Map<String, String> deleteUser(@RequestBody UserDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("아이디 : " + dto.getUserId());
		System.out.println("부서 : " + dto.getDepDto().getDep_id());
		try {
			// 사용자 삭제
			userService.deleteUser(dto);
			System.out.println("사용자 삭제됨");
			
//			map.put("msg", "success");
//			map.put("msg2", "삭제되었습니다.");
		} catch (Exception e) {
//			map.put("msg", "fail");
			e.printStackTrace();
		}
		return map;
	}
	
	// 사용자 취미 삭제
	@ResponseBody
	@PostMapping("/admin/deleteHobby")
	Map<String, String> deleteUser(@RequestBody UserHDtoPK pk) {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("pk : "+ pk);
		System.out.println("취미코드 : "+pk.getH_code_id());
		try {
			// 취미 삭제
			userService.deleteHobby(pk);
			System.out.println("취미 삭제됨");
			
//			map.put("msg", "success");
//			map.put("msg2", "삭제되었습니다.");
		} catch (Exception e) {
//			map.put("msg", "fail");
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



