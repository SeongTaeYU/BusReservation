package com.bus.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javalab.reservation.service.RouteService;
import com.javalab.reservation.vo.Route;

@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    // 경로 생성 페이지로 이동
    @GetMapping("/create")
    public String showCreateRouteForm(Model model) {
        model.addAttribute("route", new RouteDTO());
        return "route/createRouteForm";
    }

    // 경로 생성 처리
    @PostMapping("/create")
    public String createRoute(RouteDTO route) {
        routeService.createRoute(route);
        return "redirect:/route/list";
    }

    // 경로 목록 페이지로 이동
    @GetMapping("/list")
    public String showRouteList(Model model) {
        List<RouteDTO> routes = routeService.getRouteList();
        model.addAttribute("routes", routes);
        return "route/routeList";
    }

    // 경로 상세 정보 페이지로 이동
    @GetMapping("/{routeId}")
    public String showRouteDetail(@PathVariable Integer routeId, Model model) {
        RouteDTO route = routeService.getRouteById(routeId);
        if (route != null) {
            model.addAttribute("route", route);
            return "route/routeDetail";
        } else {
            // 경로가 없을 경우에 대한 처리 (예: 오류 페이지)
            return "error";
        }
    }

    // 경로 수정 페이지로 이동
    @GetMapping("/{routeId}/edit")
    public String showEditRouteForm(@PathVariable Integer routeId, Model model) {
        RouteDTO route = routeService.getRouteById(routeId);
        if (route != null) {
            model.addAttribute("route", route);
            return "route/editRouteForm";
        } else {
            // 경로가 없을 경우에 대한 처리 (예: 오류 페이지)
            return "error";
        }
    }

    // 경로 수정 처리
    @PostMapping("/{routeId}/edit")
    public String editRoute(@PathVariable Integer routeId, RouteDTO route) {
        route.setRouteId(routeId);
        routeService.updateRoute(route);
        return "redirect:/route/list";
    }

    // 경로 삭제 처리
    @PostMapping("/{routeId}/delete")
    public String deleteRoute(@PathVariable Integer routeId) {
        routeService.deleteRoute(routeId);
        return "redirect:/route/list";
    }
}
