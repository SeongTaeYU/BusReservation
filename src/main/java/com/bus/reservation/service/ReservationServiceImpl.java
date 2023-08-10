package com.bus.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javalab.reservation.dao.BusDao;
import com.javalab.reservation.dao.ReservationDao;
import com.javalab.reservation.dao.SeatDao;
import com.javalab.reservation.dao.UserDao;
import com.javalab.reservation.vo.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	// ReservationDao의 의존성 주입 (생성자 or @Autowired 등)
	private final UserDao userDao;
	private final BusDao busDao;
	private final SeatDao seatDao;
	
	private final ReservationDao reservationDao;
    
    public ReservationServiceImpl(ReservationDao reservationDao, UserDao userDao, BusDao busDao, SeatDao seatDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.busDao = busDao;
        this.seatDao = seatDao;
    }
    
    /*
     * 예약 생성
     */
    @Override
    public Reservation createReservation(Reservation reservation) {
        // ReservationDao를 사용하여 데이터베이스에 예약 생성 로직 수행
    	return reservationDao.createReservation(reservation);
    }
    
    /*
     * 예약 리스트
     */
    @Override
    public List<Reservation> getReservationList() {
    	// ReservationDao를 사용하여 데이터베이스로부터 모든 예약 정보 조회 로직 수행
    	return reservationDao.getReservationList();
    }
    /*
     * 예약 한 개 가져오는 함수(상세보기)
     */
    @Override
    public Reservation getReservationById(Integer reservationId) {
        // ReservationDao를 사용하여 데이터베이스로부터 예약 정보 조회 로직 수행
        return reservationDao.getReservationById(reservationId);
    }
    
    /*
     * 예약 수정
     */
    @Override
    public Reservation updateReservation(Reservation reservationo) {
        // ReservationDao를 사용하여 데이터베이스에서 예약 업데이트 로직 수행
        return reservationDao.updateReservation(reservationo);
    }
    
    /*
     * 예약 삭제
     */
    @Override
    public Boolean deleteReservation(Integer reservationId) {
        // ReservationDao를 사용하여 데이터베이스에서 예약 삭제 로직 수행
        return reservationDao.deleteReservation(reservationId);
    }
    
    /*
    @Override
    public List<Reservation> getReservationsByUserId(int userId) {
        // ReservationDao를 사용하여 데이터베이스로부터 해당 사용자의 예약 정보 조회 로직 수행
        List<Reservation> reservations = reservationDao.getReservationsByUserId(userId);
        
        // 각 Reservation 엔티티를 ReservationDto로 변환하여 List<ReservationDto>로 반환
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    */
}
