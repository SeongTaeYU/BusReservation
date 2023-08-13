package com.bus.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bus.reservation.dto.ReservationDTO;
import com.bus.reservation.entity.Bus;
import com.bus.reservation.entity.Reservation;
import com.bus.reservation.entity.Route;
import com.bus.reservation.entity.Seat;
import com.bus.reservation.entity.User;
import com.bus.reservation.repository.BusRepository;
import com.bus.reservation.repository.ReservationRepository;
import com.bus.reservation.repository.RouteRepository;
import com.bus.reservation.repository.SeatRepository;
import com.bus.reservation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
	
	// 의존성 주입 (생성자 or @Autowired 등)
	private final ReservationRepository reservationRepository;
	private final BusRepository busRepository;
	private final RouteRepository routeRepository;
	private final SeatRepository seatRepository;
	private final UserRepository userRepository;
    
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
    
    /*
     * 예약 생성
     */
    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        // 데이터베이스에 예약 생성 로직 수행
    	Reservation reservation = dtoToEntity(reservationDTO);
    	return reservationRepository.save(reservation);
    }//end createReservation
    
    /*
     * 예약 리스트
     */
    @Override
    public List<ReservationDTO> getReservationList() {
    	// 데이터베이스로부터 모든 예약 정보 조회 로직 수행
    	List<Reservation> reservationList = reservationRepository.findAll();
    	List<ReservationDTO> reservationDTOList = reservationList.stream()
    															 .map(entity -> entityToDto(entity))
    															 .collect(Collectors.toList());
    	return reservationDTOList;
    }//end getReservationList
    
    
    /*
     * 예약 한 개 가져오는 함수(상세보기)
     */
    @Override
    public ReservationDTO getReservationByNo(Integer reservationNo) {
        // 데이터베이스로부터 예약 정보 조회 로직 수행
    	Optional<Reservation> reservation = reservationRepository.findById(reservationNo);
    	@SuppressWarnings("unused")
		ReservationDTO reservationDTO = null;
    	if(reservation.isPresent()) {
    		reservationDTO = entityToDto(reservation.get());
    	}
        return reservation.isPresent() ? entityToDto(reservation.get()) : null;
    }//end getReservationByNo
    
    /*
     * 예약 수정
     */
    @Override
    public Reservation updateReservation(ReservationDTO reservationoDTO) {
        // 데이터베이스에서 예약 업데이트 로직 수행
    	Optional<Reservation> data = reservationRepository.findById(reservationoDTO.getReservationNo());
    	if(data.isPresent()) {
    		Reservation targetEntity = data.get();
    		targetEntity.setReservationTime(reservationoDTO.getReservationTime());
    		
    		Bus bus = busRepository.findById(reservationoDTO.getBusNo())
    														.orElseThrow(() -> new RuntimeException("버스 정보가 존재하지 않습니다."));
    		
    		Route route = routeRepository.findById(reservationoDTO.getRouteNo())
    															  .orElseThrow(() -> new RuntimeException("루트 정보가 존재하지 않습니다."));
    		
    		Seat seat = seatRepository.findById(reservationoDTO.getSeatNo())
    														   .orElseThrow(() -> new RuntimeException("좌석 정보가 존재하지 않습니다.")); 
    		
    		User user = userRepository.findById(reservationoDTO.getUserNo())
    														   .orElseThrow(() -> new RuntimeException("회원 정보가 존재하지 않습니다."));
    		
    		targetEntity.setBus(bus);
    		targetEntity.setRoute(route);
    		targetEntity.setSeat(seat);
    		targetEntity.setUser(user);
    		
    		return reservationRepository.save(targetEntity);
    	}
    	
        return null;
    }
    
    /*
     * 예약 삭제
     */
    @Override
    public Boolean deleteReservation(Integer reservationNo) {
        // 데이터베이스에서 예약 삭제 로직 수행
    	Optional<Reservation> data = reservationRepository.findById(reservationNo);
    	if(data.isPresent()) {
    		reservationRepository.delete(data.get());
    		return true;
    	}
        return false;
    }//end deleteReservation
    
    /*
     * 예약 버스 좌석 한 개  가져오는 함수(상세보기)
     * 
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
    
}//end Class ReservationServiceImpl
