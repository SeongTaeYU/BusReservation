package com.bus.reservation.controller;

import java.util.HashMap;
import java.util.Iterator;
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

import com.bus.reservation.dto.SeatDTO;
import com.bus.reservation.entity.Seat;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.SeatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/seat")
@RequiredArgsConstructor
@Slf4j
public class SeatController {

    private final SeatService seatService;

	/*
	 *  좌석 목록 페이지로 이동
	 */
    @GetMapping("/seatList")
    public String getSeatList(@RequestParam("seatNo") Integer seatNo,
    						  @ModelAttribute("seatDTO") SeatDTO seatDTO,
    						  Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        List<SeatDTO> seatList = seatService.getSeatList();
        model.addAttribute("seatList", seatList);
        
        return "seat/seatList";
    }//end getSeatList
    
    /*
     *  좌석 상세 정보 페이지로 이동
     */
    @GetMapping("/seatView")
    public String getSeatByNo(@RequestParam("seatNo") Integer seatNo,
    						  Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        SeatDTO seat = seatService.getSeatByNo(seatNo);
        if (seat != null) {
            model.addAttribute("seat", seat);
            return "seat/seatView";
        } else {
            // 좌석이 없을 경우에 대한 처리 (예: 오류 페이지)
        	return "redirect:/error";
        }//end else
    }//end getSeatByNo
	
    /*
     *  좌석 생성 페이지로 이동
     */
    @GetMapping("/seatCreate")
    public String createSeat(Model model, HttpSession session,
    						 @ModelAttribute("seat") SeatDTO seatDTO,
    						 BindingResult bindingResult) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if 문
    	
        model.addAttribute("seat", new Seat());
        
        return "seat/seatCreate";
    }//end seatCreate

    /*
     *  좌석 생성 처리
     */
    @PostMapping("/seatCreate")
    public String createSeat(Model model,
    						 @ModelAttribute("seatDTO") SeatDTO seatDTO,
    						 BindingResult bindingResult) {
    	
    	// 검증시 오류가 있으면 createForm 이동
    	if(bindingResult.hasErrors()) {
    		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    		
    		for(FieldError error : fieldErrors) {
    			log.error(error.getField() + " " + error.getDefaultMessage());
    		}//end for문
    		
    		model.addAttribute("seat", seatDTO);
    		return "seat/seatCreate";
    	}//end if문
    	
        seatService.createSeat(seatDTO);
        
        return "redirect:/seat/seatList";
    }//end seatCreate

    /*
     *  좌석 수정 페이지로 이동
     */
    @GetMapping("/seatUpdate")
    public String updateSeat(@RequestParam Integer seatNo,
    						 @ModelAttribute("seatDTO") SeatDTO seatDTO,
    						 Model model, BindingResult bindingResult,
    						 HttpSession session) {
        
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
    	Map<String,Object> seatVo = new HashMap<>();
    	
    	SeatDTO seat = seatService.getSeatByNo(seatNo);
    	seatVo.put("seat", seat);
    	
    	model.addAttribute("seatVo", seatVo);
    	
    	return "redirect:/seat/seatUpdate";
    }//end updateSeat

    /*
     *  좌석 수정 처리
     */
    @PostMapping("/seatUpdate")
    public String updateSeat(Model model, BindingResult bindingResult,
    						 @RequestParam Integer seatNo,
    						 @ModelAttribute("seatDTO")SeatDTO seatDTO) {
        
    	Map<String, Object> seatVo = new HashMap<>();
    	SeatDTO seat = seatService.getSeatByNo(seatNo);
    	seatVo.put("seat", seat);
    	
    	model.addAttribute("seatVo", seatVo);
        
        return "redirect:/seat/seatList";
    }//end updateSeat

    /*
     *  좌석 삭제 처리
     */
    @PostMapping("/seatDelete/{seatNo}")
    public String deleteSeat(@PathVariable Integer seatNo,
    						 Model model, HttpSession session) {
    	
    	User user = (User) session.getAttribute("user");
    	if(user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        boolean delete = seatService.deleteSeat(seatNo);
        
        return "redirect:/seat/seatList";
    }//end deleteSeat
    
}//end Class SeatController
