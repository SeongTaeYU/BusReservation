package com.bus.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bus.reservation.dto.ReservationDTO;
import com.bus.reservation.entity.Reservation;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    // 메인 페이지로 이동
    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }
    
    /*
     *  예약 목록 페이지로 이동
     */
    @GetMapping("/reservationList")
    public String getReservationList(@RequestParam("reservationNo") Integer reservationNo,
    								 @ModelAttribute("reservationDTO") ReservationDTO reservationDTO,
    								 Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        List<ReservationDTO> reservationList = reservationService.getReservationList();
        model.addAttribute("reservation", reservationList);
        
        return "reservation/reservationList";
    }//end getReservationList
    
    /*
     *  예약 상세 정보 페이지로 이동
     */
    @GetMapping("/reservationView")
    public String getReservationByNo(@RequestParam("reservationNo") Integer reservationNo,
    								 Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
    	ReservationDTO reservation = reservationService.getReservationByNo(reservationNo);
        
    	model.addAttribute("reservation", reservation);
    	
    	return "reservation/reservationView";
    }//end getReservationByNo

    /*
     *  예약 생성 페이지로 이동
     */
    @GetMapping("/reservationCreate")
    public String createReservation(Model model, BindingResult bindingResult,
    								@ModelAttribute("reservation") ReservationDTO reservationDTO,
    								HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if 문
    	
        model.addAttribute("reservation", new Reservation());
        
        return "reservation/reservationCreate";
    }//end createReservation

    // 경로 생성 처리
    @PostMapping("/reservationCreate")
    public String createReservation(Model model, BindingResult bindingResult,
    								@ModelAttribute("reservationDTO") ReservationDTO reservationDTO) {
    	
    	//검증시 오류가 있으면 createForm 이동
    	if(bindingResult.hasErrors()) {
    		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    		
    		for(FieldError error : fieldErrors) {
    			log.error(error.getField() + " " + error.getDefaultMessage());
    		}//end for문
    		
    		model.addAttribute("reservation", reservationDTO);
    		return "reservation/reservationCreate";
    	}//end if문
    	
        reservationService.createReservation(reservationDTO);
        
        return "redirect:/bus-reservation/route/list";
    }//end createReservation
    
    // 예약 수정 페이지로 이동
    @GetMapping("/reservationUpdate")
    public String updateReservation(@RequestParam Integer reservationNo,
    								@ModelAttribute("reservationDTO") ReservationDTO reservationDTO,
    								Model model, BindingResult bindingResult,
    								HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
    	Map<String, Object> reservationVo = new HashMap<>();
    	
        ReservationDTO reservation = reservationService.getReservationByNo(reservationNo);
        reservationVo.put("reservation",reservation);
        
        model.addAttribute("reservationVo", reservationVo);
        
        return "redirect:/reservation/reservationUpdate";
    }//end updateReservation

    // 예약 수정 처리
    @PostMapping("/updateReservationUpdate")
    public String updateReservation(Model model, BindingResult bindingResult,
    								@RequestParam Integer reservationNo, 
    								@ModelAttribute("reservationDTO") ReservationDTO reservationDTO) {
        
    	Map<String, Object> reservationVo = new HashMap<>();
    	ReservationDTO reservation = reservationService.getReservationByNo(reservationNo);
        reservationVo.put("reservation", reservation);
        
        model.addAttribute("reservationVo", reservationVo);
    	
    	return "redirect:/reservation/reservationList";
    }//end updateReservation

    // 예약 삭제 처리
    @PostMapping("/reservationDelete/{reservationNo}")
    public String deleteReservation(@PathVariable Integer reservationNo,
    						  		Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if(user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        boolean delete = reservationService.deleteReservation(reservationNo);
        
        return "redirect:/reservation/reservationList";
    }//end deleteReservation
	
}//end Class ReservationController
