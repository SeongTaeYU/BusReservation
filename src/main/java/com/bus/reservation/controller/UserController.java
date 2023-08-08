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
import org.springframework.web.bind.annotation.ResponseBody;

import com.javalab.reservation.service.UserService;
import com.javalab.reservation.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private UserService userService;
	
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
	public String processLogin(@RequestBody UserDTO user,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   Model model) {
		UserDTO users = userService.login(user);
		
		String result = "";
		
		if (users != null) {
			//로그인 성공
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60*60*24);	// 세션 유지시간 24시간
			session.setAttribute("user", users);		// 세션에 사용자 정보 저장
			
			// 쿠키 생성 및 전송
			Cookie cookie = new Cookie("userName", user.getUserName());
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
		
		// Delte the Cookie
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
	 * 회원가입 폼 불러오기
	 */
	@GetMapping("userCreate")
	public String createForm(Model model,
							@ModelAttribute("user") UserDTO user,
							BindingResult bindingResult) {
		model.addAttribute("user", new UserDTO());
		return "user/userCreateForm";
	}
	
	/*
	 * 회원가입 처리 함수
	 */
	@PostMapping("/userCreate")
	public String userCreate(@ModelAttribute() @Valid UserDTO user,
							 BindingResult bindingResult,
							 Model model) {
		if (bindingResult.hasErrors()) {
			return "/user/userCreateForm";
		}
		userService.createUser(user);
		return "redirect:/user/login";
	}//end userCreate
	
	/*
	 * 회원 정보 수정
	 */
	@PostMapping("/userUpdate")
	public String userUpdate(@ModelAttribute @Valid UserDTO user,
							BindingResult bindingResult,
							HttpSession session,
							Model model) {
		
		if(bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				log.error(error.getField() + " " + error.getDefaultMessage());
			}//end for문
			model.addAttribute("user",user);
			System.out.println("@@@회원수정Post : "+user);
			return "user/userUpdate";
		}//end if 문
		
		userService.updateUser(user);
		
		UserDTO users = (UserDTO) session.getAttribute("user");
		users.setUserName(user.getUserName());
		return "redirect:/user/mypage";
	}//end userUpdate
	
}//end UserController
