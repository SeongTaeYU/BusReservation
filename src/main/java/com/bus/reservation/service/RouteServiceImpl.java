package com.bus.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.javalab.reservation.dao.RouteDao;
import com.javalab.reservation.vo.Route;

public class RouteServiceImpl implements RouteService{

	// UserDao의 의존성 주입 (생성자 or @Autowired 등)
	@Autowired
	private RouteDao routeDao;

	/*
	 * 경로 생성
	 */
    @Override
    public Route createRoute(Route route) {
        // RouteDao를 사용하여 데이터베이스에 경로 생성 로직 수행
        return routeDao.createRoute(route);
    }
    
    /*
     * 경로 리스트
     */
    @Override
    public List<Route> getRouteList(){
    	// RouteDao를 사용하여 데이터베이스로부터 모든 경로 정보 조회 로직 수행
    	return routeDao.getRouteList();
    }
    
    
    /*
     * 경로 한 개 가져오는 함수(상세보기)
     */
    @Override
    public Route getRouteById(Integer routeId) {
        // RouteDao를 사용하여 데이터베이스로부터 경로 정보 조회 로직 수행
        return routeDao.getRouteById(routeId);
        
    }
    
    /*
     * 경로 수정하는 함수
     */
    @Override
    public Route updateRoute(Route route) {
        // RouteDao를 사용하여 데이터베이스에서 경로 업데이트 로직 수행
        return routeDao.updateRoute(route);
    }
    
    /*
     * 경로 삭제하는 함수
     */
    @Override
    public Boolean deleteRoute(Integer routeId) {
        // UserDao를 사용하여 데이터베이스에서 사용자 삭제 로직 수행
        return routeDao.deleteRoute(routeId);
    }
    
}
