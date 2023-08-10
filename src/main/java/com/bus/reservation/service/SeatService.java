package com.bus.reservation.service;

import java.util.List;

import com.bus.reservation.dto.SeatDTO;
import com.bus.reservation.entity.Bus;
import com.bus.reservation.entity.Seat;

public interface SeatService {
	
    Seat createSeat(Seat seat);					// 좌석 생성
    List<Seat> getSeatList();					// 좌석 리스
    Seat getSeatById(Integer seatId);			// 좌석 한 개 가져오는 함수(상세보기)
    Seat updateSeat(Seat seat);					// 좌석 수정하는 함수
    Boolean deleteSeat(Integer seatId);			// 좌석 삭제하는 함수
    
    Seat getSeatByBusId(Integer busId);			// 해당 버스의 좌석 정보 조회 함수
    
    /*
     * DTO --> Entity 전환을 위한 default 메소드
     */
    default Seat dtoToEntity(SeatDTO seatDto) {
    	// 화면에서 전달된 busNo로 객체 생성
    	Bus bus = Bus.builder()
    			     .busNo(seatDto.getBusNo())
    			     .build();
    	
    	Seat entity = Seat.builder()
    					  .seatNo(seatDto.getSeatNo())
    					  .seatNumber(seatDto.getSeatNumber())
    					  .isReserved(seatDto.getIsReserved())
    					  .bus(bus)
    					  .build();
    	
    	return entity;
    }
    
    /*
     * Entity --> DTO 전환을 위한 default 메소드
     */
    default SeatDTO entityToDto(Seat seatEntity) {
    	
    	SeatDTO seatDto = SeatDTO.builder()
    					         .seatNo(seatEntity.getSeatNo())
    					         .seatNumber(seatEntity.getSeatNumber())
    					         .busNo(seatEntity.getBus().getBusNo())
    					         .busNumber(seatEntity.getSeatNumber())
    					         .regDate(seatEntity.getRegDate())
    					         .modDate(seatEntity.getModDate())
    			 			     .build();
    	
    	return seatDto;
    }
}
