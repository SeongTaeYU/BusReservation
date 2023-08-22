package com.bus.reservation.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bus.reservation.dto.BusDTO;
import com.bus.reservation.dto.RouteDTO;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.BusService;
import com.bus.reservation.service.RouteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final RouteService routeService;
	private final BusService busService;
	

		@GetMapping("/")
		public String home(Model model, HttpSession session) {
			
			 User user = (User) session.getAttribute("user");
			    if (user == null) {
			    	
			    }
			List<RouteDTO> routeList = routeService.getRouteList();
			model.addAttribute("routeList",routeList);
			List<BusDTO> busList = busService.getBusList();
			model.addAttribute("busList",busList);
			
			//new Date() : java.util.Date 인식안되면 지웠다가 새로 타이핑
			model.addAttribute("date", new Date());	
			//return "redirect:/reservation/reservationMain";
			return "home";
		}	
	}