package com.bus.reservation.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bus.reservation.dto.BusDTO;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.BusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
@Slf4j
public class BusController {
	
	private final BusService busService;
	
	/*
	 * 모든 버스 목록 조회
	 */
	@GetMapping("/busList")
	public String getBusList(Model model,
							@RequestParam("busNo") Integer busNo,
							@ModelAttribute("busDTO") BusDTO busDTO,
							HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return "redirect:/user/login";
		}//end if문
		
		List<BusDTO> busList = busService.getBusList();
		busList.sort(Comparator.comparing(BusDTO::getBusNo).reversed());
		
		model.addAttribute("busList", busList);
		
		return "bus/busList";
	}//end getBusList
	
	/*
	 * 버스 정보 보기(상세보기)
	 */
	@GetMapping("busView")
	public String getBusById(@RequestParam("busId") Integer busId,
							Model model) {
		BusDTO bus = busService.getBusById(busId);
		
		model.addAttribute("bus", bus);
		
		return "bus/busView";
	}//end getBusById
	
	/*
	 * 버스 등록 폼
	 */
	@GetMapping("/busCreate")
	public String createBus(BusDTO bus, Model model) {
		
		model.addAttribute("bus", new BusDTO());
		
		return "bus/busCreate";
	}//end createBus
	
	/*
	 * 버스 등록
	 */
	@PostMapping("/busCreate")
	public String createBus(BusDTO bus) {
		
		// 버스 등록(저장)
		BusDTO result = busService.createBus(bus);
		
		// 저장후 목록 출력 컨트롤러 호출, redirect하면 busList 화면의 주소창이 변경됨.
		return "redirect:/bus/busList";
	}//end createBus
	
	/*
	 * 버스 수정폼을 보여주는 메소드
	 */
	@GetMapping("/busUpdate")
	public String updateBus(Integer busId, Model model) {
		
		Map<String,Object> busVo = new HashMap<>();
		BusDTO bus = busService.getBusById(busId);
		busVo.put("bus", bus);
		
		model.addAttribute("busVo",busVo);
		
		return "redirect:/bus/busUpdate";
	}//end updateBus
	
	/*
	 *  버스 수정한 내용을 데이터베이스에 반영하는 메소드
	 */
	@PostMapping("/busUpdate")
	public String updateBus(BusDTO bus) {
		
		busService.updateBus(bus);
		
		return "redirect:/bus/busList";
	}//end updateBus
	
	/*
	 * 버스 삭제 처리
	 */
	@GetMapping("busDelete/{busId}")
	public String deleteBus(@PathVariable Integer busId, Model model) {
		
		boolean delete = busService.deleteBus(busId);
		
		return "redirect:/bus/busList";
	}
	
	
}//end BusController
