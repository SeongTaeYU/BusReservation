package com.bus.reservation.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

		@GetMapping("/")
		public String home(Model model) {
			//new Date() : java.util.Date 인식안되면 지웠다가 새로 타이핑
			model.addAttribute("date", new Date());	
			return "redirect:/reservation/reservationMain";
		}	
	}