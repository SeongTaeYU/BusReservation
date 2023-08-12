package com.bus.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bus.reservation.dto.RouteDTO;
import com.bus.reservation.entity.Route;
import com.bus.reservation.repository.RouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

	// 의존성 주입 (생성자 or @Autowired 등)
	private final RouteRepository routeRepository;
	
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/*
	 * 경로 생성
	 */
    @Override
    public Route createRoute(RouteDTO routeDTO) {
        // 데이터베이스에 경로 생성 로직 수행
    	Route route = dtoToEntity(routeDTO);
        return routeRepository.save(route);
    }
    
    /*
     * 경로 리스트
     */
    @Override
    public List<RouteDTO> getRouteList(){
    	// 데이터베이스로부터 모든 경로 정보 조회 로직 수행
    	List<Route> routeList = routeRepository.findAll();
    	List<RouteDTO> routeDTOList = routeList.stream()
    										   .map(entity -> entityToDto(entity))
    										   .collect(Collectors.toList());
    			
    	return routeDTOList;
    }
    
    
    /*
     * 경로 한 개 가져오는 함수(상세보기)
     */
    @Override
    public RouteDTO getRouteByNo(Integer routeNo) {
        // 데이터베이스로부터 경로 정보 조회 로직 수행
    	Optional<Route> route = routeRepository.findById(routeNo);
    	@SuppressWarnings("unused")
		RouteDTO routeDTO = null;
    	if(route.isPresent()) {
    		routeDTO = entityToDto(route.get());
    	}
        return route.isPresent() ? entityToDto(route.get()) : null;
        
    }
    
    /*
     * 경로 수정하는 함수
     */
    @Override
    public Route updateRoute(RouteDTO routeDTO) {
        // 데이터베이스에서 경로 업데이트 로직 수행
    	Optional<Route> data = routeRepository.findById(routeDTO.getRouteNo());
    	if(data.isPresent()) {
    		Route targetEntity = data.get();
    		targetEntity.setRouteNo(routeDTO.getRouteNo());
    		targetEntity.setOrigin(routeDTO.getOrigin());
    		targetEntity.setDestination(routeDTO.getDestination());
    		targetEntity.setDepartureTime(routeDTO.getDepartureTime());
    		targetEntity.setArrivalTime(routeDTO.getArrivalTime());
    		targetEntity.setDate(routeDTO.getDate());
    		
    		return routeRepository.save(targetEntity);
    	}
        return null;
    }
    
    /*
     * 경로 삭제하는 함수
     */
    @Override
    public Boolean deleteRoute(Integer routeNo) {
        // 데이터베이스에서 사용자 삭제 로직 수행
    	Optional<Route> data = routeRepository.findById(routeNo);
    	if(data.isPresent()) {
    		routeRepository.delete(data.get());
    		return true;
    	}
        return false;
    }
    
}
