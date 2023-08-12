package com.bus.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bus.reservation.dto.SeatDTO;
import com.bus.reservation.entity.Bus;
import com.bus.reservation.entity.Seat;
import com.bus.reservation.repository.BusRepository;
import com.bus.reservation.repository.SeatRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
	
	// 의존성 주입 (생성자 or @Autowired 등)
	private final SeatRepository seatRepository;
	private final BusRepository busRepository;

	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/*
	 * 좌석 생성
	 */
    @Override
    public Seat createSeat(SeatDTO seatDTO) {
        // SeatDao를 사용하여 데이터베이스에 좌석 생성 로직 수행
    	Seat seat = dtoToEntity(seatDTO);
        return seatRepository.save(seat);
    }

    /*
     * 좌석 모든 정보 리스트
     */
    @Override
    public List<SeatDTO> getSeatList() {
    	// SeatDao를 사용하여 데이터베이스로부터 모든 좌석 정보 조회 로직 수행
    	List<Seat> seatList  = seatRepository.findAll();
    	List<SeatDTO> seatDTOList = seatList.stream()
    										.map(entity -> entityToDto(entity))
    										.collect(Collectors.toList());
    	return seatDTOList;
    }
    
    /*
     * 좌석 한 개 가져오는 함수(상세보기)
     */
    @Override
    public SeatDTO getSeatByNo(Integer seatNo) {
        // SeatDao를 사용하여 데이터베이스로부터 좌석 정보 조회 로직 수행
    	Optional<Seat> seat = seatRepository.findById(seatNo);
    	@SuppressWarnings("unused")
		SeatDTO seatDTO = null;
    	if(seat.isPresent()) {
    		seatDTO = entityToDto(seat.get());
    	}
        return seat.isPresent() ? entityToDto(seat.get()) : null;
        
    }
    
    /*
     * 좌석 수정하는 함수
     */
    @Override
    public Seat updateSeat(SeatDTO seatDTO) {
        // 데이터베이스에서 좌석 업데이트 로직 수행
    	Optional<Seat> data = seatRepository.findById(seatDTO.getSeatNo());
    	if(data.isPresent()) {
    		Seat targetEntity = data.get();
    		targetEntity.setSeatNumber(seatDTO.getSeatNumber());
    		targetEntity.setIsReserved(seatDTO.getIsReserved());
    		
    		Bus bus = busRepository.findById(seatDTO.getBusNo())
    				.orElseThrow(() ->  new RuntimeException("버스 정보가 존재하지 않습니다."));
    		
    		targetEntity.setBus(bus);
    		
    		return seatRepository.save(targetEntity);
    	}
        return null;
    }
    
    /*
     * 좌석 삭제하는 함수
     */
    @Override
    public Boolean deleteSeat(Integer seatNo) {
        // 데이터베이스에서 좌석 삭제 로직 수행
    	Optional<Seat> data = seatRepository.findById(seatNo);
    	if(data.isPresent()) {
    		seatRepository.delete(data.get());
    		return true;
    	}
        return false;
    }
    
    
    /*
     * 해당 버스의 좌석 정보 조회 로직 수행
    
    @Override
    public SeatDTO getSeatByBusNo(Integer busNo) {
        // 데이터베이스로부터 해당 버스의 좌석 정보 조회 로직 수행
    	Optional<Bus> bus = busRepository.findById(busNo);
    	SeatDTO seatDTO = null;
    	if(bus.isPresent()) {
    		seatDTO = entityToDto(bus.get());
    	}
        return bus.isPresent() ? entityToDto(bus.get()) : null;
    }
     */
   
}
