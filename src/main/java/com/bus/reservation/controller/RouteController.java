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

import com.bus.reservation.dto.RouteDTO;
import com.bus.reservation.entity.Route;
import com.bus.reservation.entity.User;
import com.bus.reservation.service.RouteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
@Slf4j
public class RouteController {

    private final RouteService routeService;
    
    /*
     *  경로 목록 페이지로 이동
     */
    @GetMapping("/routeList")
    public String getRouteList(Model model,
    						   @RequestParam("routeNo") Integer routeNo,
    						   @ModelAttribute("routeDTO") RouteDTO routeDTO,
    						   HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        List<RouteDTO> routeList = routeService.getRouteList();
        model.addAttribute("routeList", routeList);
        
        return "route/routeList";
    }//end getRouteList

    /*
     *  경로 상세 정보 페이지로 이동
     */
    @GetMapping("routeView")
    public String getRouteByNo(@RequestParam("routeNo") Integer routeNo,
    							  HttpSession session,
    							  Model model) {
    	
    	User user = (User) session.getAttribute("user");
    	if( user == null ) {
    		return "redirect:/user/login";
    	}//end if문
    	
        RouteDTO route = routeService.getRouteByNo(routeNo);
        if (route != null) {
            model.addAttribute("route", route);
            return "route/routeView";
        } else {
            // 경로가 없을 경우에 대한 처리 (예: 오류 페이지)
            return "error";
        }//end else
    }//end getRouteByNo
    

    /*
     *  경로 생성 페이지로 이동
     */
    @GetMapping("/routeCreate")
    public String createRoute(Model model,
    						  @ModelAttribute("route") RouteDTO routeDTO,
    						  BindingResult bindingResult,
    						  HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if 문
    	
        model.addAttribute("route", new Route());
        
        return "route/routeCreate";
    }//end createRoute

    /*
     *  경로 생성 처리
     */
    @PostMapping("/routeCreate")
    public String createRoute(Model model, 
    						  @ModelAttribute("routeDTO") RouteDTO routeDTO,
    						  BindingResult bindingResult) {
    	
    	// 검증시 오류가 있으면 createFrom 이동
    	if(bindingResult.hasErrors()) {
	    	List<FieldError> fieldErrors = bindingResult.getFieldErrors();
	    	
	    	for (FieldError error : fieldErrors) {
	    		log.error(error.getField() + " " + error.getDefaultMessage());
	    	}//end for문
	    	
	        model.addAttribute("route", routeDTO);
	        
	        return "route/routeCreate";
    	}//end if문
    	
    	routeService.createRoute(routeDTO);
    	
    	// 저장후 목록 출력 컨트롤러 호출, redirect하면 routeList 화면의 주소창이 변경됨.
    	return "redirect:/route/routeList";
    }//end createRoute



    /*
     *  경로 수정 페이지로 이동
     */
    @GetMapping("/routeUpdate")
    public String createRoute(@RequestParam Integer routeNo,
    						  @ModelAttribute("routeDTO") RouteDTO routeDTO,
    						  BindingResult bindingResult,
    					  	  HttpSession session,
    						  Model model) {
    	
    	User user = (User) session.getAttribute("user");
    	if (user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
    	Map<String,Object> routeVo = new HashMap<>();
        RouteDTO route = routeService.getRouteByNo(routeNo);
        routeVo.put("route", route);
        
        model.addAttribute("routeVo", routeVo);
        
        return "redirect:/route/routeUpdate";
        
    }//end createRoute

    /*
     *  경로 수정 처리
     */
    @PostMapping("/routeUpdate")
    public String updateRoute(Model model, BindingResult bindingResult,
    						  @RequestParam Integer routeNo,
    						  @ModelAttribute("routeDTO") RouteDTO routeDTO) {
        
    	Map<String, Object> routeVo = new HashMap<>();
    	RouteDTO route = routeService.getRouteByNo(routeNo);
    	routeVo.put("route", route);
    	
    	model.addAttribute("routeVo", routeVo);
    	
        return "redirect:/route/routeList";
    }//end updateRoute

    /*
     *  경로 삭제 처리
     */
    @PostMapping("routeDelete/{routeNo}")
    public String deleteRoute(@PathVariable Integer routeNo,
    						  HttpSession session,
    						  Model model) {
    	
    	User user = (User) session.getAttribute("user");
    	
    	if(user == null) {
    		return "redirect:/user/login";
    	}//end if문
    	
        boolean delete = routeService.deleteRoute(routeNo);
        
        return "redirect:/route/list";
    }//end deleteRoute
    
}//end class RouteController
