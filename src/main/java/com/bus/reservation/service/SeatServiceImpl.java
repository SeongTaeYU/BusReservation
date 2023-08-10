package com.bus.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.reservation.dao.SeatDao;
import com.javalab.reservation.vo.Seat;

@Service
public class SeatServiceImpl implements SeatService {
	
	// SeatDao의 의존성 주입 (생성자 or @Autowired 등)
	@Autowired
	private SeatDao seatDao;

	/*
	 * 좌석 생성
	 */
    @Override
    public Seat createSeat(Seat seat) {
        // SeatDao를 사용하여 데이터베이스에 좌석 생성 로직 수행
        return seatDao.createSeat(seat);
    }

    /*
     * 좌석 모든 정보 리스트
     */
    @Override
    public List<Seat> getSeatList() {
    	// SeatDao를 사용하여 데이터베이스로부터 모든 좌석 정보 조회 로직 수행
    	return seatDao.getSeatList();
    }
    
    /*
     * 좌석 한 개 가져오는 함수(상세보기)
     */
    @Override
    public Seat getSeatById(Integer seatId) {
        // SeatDao를 사용하여 데이터베이스로부터 좌석 정보 조회 로직 수행
        return seatDao.getSeatById(seatId);
        
    }
    
    /*
     * 좌석 수정하는 함수
     */
    @Override
    public Seat updateSeat(Seat seat) {
        // SeatDao를 사용하여 데이터베이스에서 좌석 업데이트 로직 수행
        return seatDao.updateSeat(seat);
    }
    
    /*
     * 좌석 삭제하는 함수
     */
    @Override
    public Boolean deleteSeat(Integer seatId) {
        // SeatDao를 사용하여 데이터베이스에서 좌석 삭제 로직 수행
        return seatDao.deleteSeat(seatId);
    }
    
    
    /*
     * 해당 버스의 좌석 정보 조회 로직 수행
     */
    @Override
    public Seat getSeatByBusId(Integer busId) {
        // SeatDao를 사용하여 데이터베이스로부터 해당 버스의 좌석 정보 조회 로직 수행
        return seatDao.getSeatByBusId(busId);
    }
    
   
}
