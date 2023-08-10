package com.bus.reservation.service;

import java.util.List;

import com.bus.reservation.dto.RouteDTO;
import com.bus.reservation.entity.Route;


public interface RouteService {
    Route createRoute(Route route);				// 경로 생성
    List<Route> getRouteList();					// 경로 리스트
    Route getRouteById(Integer routeId);		// 경로 한 개 가져오는 함수(상세보기)
    Route updateRoute(Route route);				// 경로 수정하는 함수
    Boolean deleteRoute(Integer routeId);		// 경로 삭제하는 함수
    
    /*
     * DTO --> Entity 전환을 위한 default 메소드
     */
    default Route dtoToEntity(RouteDTO routeDto) {
    	
    	Route entity = Route.builder()
    						.routeNo(routeDto.getRouteNo())
    						.origin(routeDto.getOrigin())
    						.destination(routeDto.getDestination())
    						.departureTime(routeDto.getDepartureTime())
    						.arrivalTime(routeDto.getArrivalTime())
    						.date(routeDto.getDate())
    			            .build();
    	
    	return entity;
    }
    
    /*
     * Entity --> DTO 전환을 위한 default 메소드
     */
    default RouteDTO entityToDto(Route routeEntity) {
    	RouteDTO routeDto = RouteDTO.builder()
    								.routeNo(routeEntity.getRouteNo())
    								.origin(routeEntity.getOrigin())
    								.destination(routeEntity.getDestination())
    								.departureTime(routeEntity.getDepartureTime())
    								.arrivalTime(routeEntity.getArrivalTime())
    								.date(routeEntity.getDate())
    								.regDate(routeEntity.getRegDate())
    								.modDate(routeEntity.getModDate())
    								.build();
    	
    	return routeDto;
    }
}
