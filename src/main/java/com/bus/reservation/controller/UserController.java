package com.bus.reservation.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bus.reservation.dto.UserDTO;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	/*
	 * loginForm 불러오는 함수
	 */
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "user/loginForm";
	}//end loginForm
	
	/*
	 * login Process 실행 함수
	 * - 세션 24시간 유지 시간 설정
	 * - 쿠키 유효시간 24시간 설정
	 * - 로그인 성공 or 실패 기능 구현
	 */
	@PostMapping("/login")
	@ResponseBody
	public String processLogin(@RequestBody UserDTO userDTO,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   Model model) {
		User user = userService.login(userDTO);
		
		System.out.println("ID : "+ userDTO.getUserId());
		System.out.println("ID : "+ userDTO.getPassword());
		
		String result = "";
		
		if (user != null) {
			//로그인 성공
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60*60*24);	// 세션 유지시간 24시간
			session.setAttribute("user", user);		// 세션에 사용자 정보 저장
			
			// 쿠키 생성 및 전송
			Cookie cookie = new Cookie("userName", userDTO.getUserName());
			cookie.setMaxAge(86400);	// 쿠키 유효 시간 설정(예: 24시간)
			response.addCookie(cookie);
			result = "success";
			
			return result;
		} else {
			// 로그인 실패
			model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
			result = "fail";
			return result;
		} 
	}//end processLogin
	
	/*
	 * logout 기능 함수
	 * - Cookie 삭제
	 */
	public String logout(HttpServletRequest request,
						 HttpServletResponse response,
						 HttpSession session) {
		
		session = request.getSession();
		session.invalidate();
		
		// Delete the Cookie
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("userName")) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				} //end if문
			}//end for문
		}//end if 문
		return "redirect:/";
	}//end logout
	
	/*
	 * ID 체크 함수
	 */
	
	@PostMapping("/idcheck")
	@ResponseBody
	public boolean idExist(Model model, 
						  @ModelAttribute("userId") String userId,
						  BindingResult bindingResult) {
		
		log.info("idcheck 메소드");
		
		return userService.idExist(userId);
		
	}//end idExist
	
	/*
	 * MyPage 함수
	 */
	@GetMapping("/mypage")
	public String mypage(Model model,
						 HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			return "redirect:/user/login";
		}//end if
		
		Integer userNo = user.getUserNo();
		
		List<UserDTO> getUserList = userService.getUserListByNo(userNo); 
		
		model.addAttribute("user", user);
		model.addAttribute("getUserList", getUserList);
		
		return "user/mypage";
		
	}//end mypage
	
	/*
	 * 회원가입 폼 불러오기
	 */
	@GetMapping("userCreate")
	public String createForm(Model model,
							@ModelAttribute("user") UserDTO user,
							BindingResult bindingResult) {
		model.addAttribute("user", new User());
		return "user/userCreateForm";
	}
	
	/*
	 * 회원가입 처리(저장) 함수
	 */
	@PostMapping("/userCreate")
	public String userCreate(@ModelAttribute() @Valid UserDTO userDTO,
							 BindingResult bindingResult,
							 Model model) {
		if (bindingResult.hasErrors()) {
			return "/user/userCreateForm";
		}
		userService.createUser(userDTO);
		return "redirect:/user/login";
	}//end userCreate
	
	/*
	 * 회원 정보 수정 폼
	 */
	@GetMapping("/userUpdate")
	public void userUpdate(@RequestParam("userNo") Integer userNo,
						   @ModelAttribute("userDTO") UserDTO userDTO,
						   BindingResult bindingResult,
						   Model model) {
		
		UserDTO userDto = userService.getUserByNo(userNo);
		
		model.addAttribute(userDto);
		
	}//end userUpdate
	
	/*
	 * 회원 정보 수정 저장
	 */
	@PostMapping("/userUpdate")
	public String userUpdate(@ModelAttribute @Valid UserDTO userDTO,
							BindingResult bindingResult,
							HttpSession session,
							Model model) {
		
		if(bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				log.error(error.getField() + " " + error.getDefaultMessage());
			}//end for문
			model.addAttribute("user",userDTO);
			System.out.println("@@@회원수정Post : "+userDTO);
			return "user/userUpdate";
		}//end if 문
		
		userService.updateUser(userDTO);
		
		User user = (User) session.getAttribute("user");
		user.setUserName(userDTO.getUserName());
		return "redirect:/user/mypage";
	}//end userUpdate
	
}//end UserController
