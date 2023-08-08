package com.bus.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javalab.reservation.service.SeatService;
import com.javalab.reservation.vo.Seat;

@Controller
@RequestMapping("/seat")
public class SeatController {

	@Autowired
    private SeatService seatService;

	/*
	 *  좌석 목록 페이지로 이동
	 */
    @GetMapping("/seatList")
    public String getSeatList(@ModelAttribute("seat") SeatDTO seat,
    						 Model model) {
        List<SeatDTO> seats = seatService.getSeatList();
        model.addAttribute("seats", seats);
        return "seat/seatList";
    }//end getSeatList
    
    /*
     *  좌석 상세 정보 페이지로 이동
     */
    @GetMapping("/seatView")
    public String getSeatById(@PathVariable Integer seatId, Model model) {
        SeatDTO seat = seatService.getSeatById(seatId);
        if (seat != null) {
            model.addAttribute("seat", seat);
            return "seat/seatView";
        } else {
            // 좌석이 없을 경우에 대한 처리 (예: 오류 페이지)
        	return "redirect:/error";
        }
    }//end getSeatById
	
    /*
     *  좌석 생성 페이지로 이동
     */
    @GetMapping("/seatCreate")
    public String createSeat(Model model) {
        model.addAttribute("seat", new SeatDTO());
        return "seat/seatCreate";
    }//end seatCreate

    /*
     *  좌석 생성 처리
     */
    @PostMapping("/seatCreate")
    public String seatCreate(SeatDTO seat) {
        seatService.createSeat(seat);
        return "redirect:/seat/seatList";
    }//end seatCreate

    /*
     *  좌석 수정 페이지로 이동
     */
    @GetMapping("/seatUpdate")
    public String showEditSeatForm(@PathVariable Integer seatId, Model model) {
        
    	SeatDTO seat = seatService.getSeatById(seatId);
        if (seat != null) {
            model.addAttribute("seat", seat);
            return "seat/seatUpdate";
        } else {
            // 좌석이 없을 경우에 대한 처리 (예: 오류 페이지)
            return "error";
        }
    }

    /*
     *  좌석 수정 처리
     */
    @PostMapping("/seatUpdate")
    public String editSeat(@PathVariable Integer seatId, SeatDTO seat) {
        
    	seatService.updateSeat(seat);
        
        return "redirect:/seat/seatList";
    }

    /*
     *  좌석 삭제 처리
     */
    @PostMapping("/seatDelete/{seatId}")
    public String deleteSeat(@PathVariable Integer seatId, Model model) {
        boolean delete = seatService.deleteSeat(seatId);
        return "redirect:/seat/seatList";
    }
}
