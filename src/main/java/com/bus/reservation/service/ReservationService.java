package com.bus.reservation.service;

import java.util.List;

import com.bus.reservation.dto.ReservationDTO;
import com.bus.reservation.entity.Bus;
import com.bus.reservation.entity.Reservation;
import com.bus.reservation.entity.Route;
import com.bus.reservation.entity.Seat;



public interface ReservationService {
	Reservation createReservation(Reservation reservation);			   // 예약 생성
	List<Reservation> getReservationList();	   						   // 예약 리스트
	Reservation getReservationById(Integer reservationId);			   // 예약 한 개 가져오는 함수(상세보기)
    Reservation updateReservation(Reservation reservation);			   // 예약 수정 함수
    Boolean deleteReservation(Integer reservationId);	 	 	   	   // 예약 삭제 함수
    
    /*
     * DTO --> Entity 전환을 위한 default 메소드
     */
    default Reservation dtoToEntity(ReservationDTO reservationDto) {
    	
    	// 화면에서 전달된 bus로 객체 생성
    	Bus bus = Bus.builder()
    				 .busNo(reservationDto.getBusNo())
    				 .busNumber(reservationDto.getBusNumber())
    				 .build();
    	
    	// 화면에서 전달된 seat로 객체 생성
		Seat seat = Seat.builder()
    					.seatNo(reservationDto.getSeatNo())
    					.seatNumber(reservationDto.getSeatNumber())
    					.build();

    	// 화면에서 전달된 route로 객체 생성
    	Route route = Route.builder()
    					   .routeNo(reservationDto.getRouteNo())
    					   .origin(reservationDto.getOrigin())
    					   .destination(reservationDto.getDestination())
    					   .build();
    	
    	Reservation entity = Reservation.builder()
    									.reservationNo(reservationDto.getReservationNo())
    									.reservationTime(reservationDto.getReservationTime())
    									.bus(bus)
    									.seat(seat)
    									.route(route)
    									.build();
    	
    	return entity;
    }
    
    /*
     * Entity --> DTO 전환을 위한 default 메소드
     */
    default ReservationDTO entityToDto(Reservation reservationEntity) {
    	
    	ReservationDTO reservationDTO = ReservationDTO.builder()
    												  .reservationNo(reservationEntity.getReservationNo())
    												  .reservationTime(reservationEntity.getReservationTime())
    												  .busNo(reservationEntity.getBus().getBusNo())
    												  .busNumber(reservationEntity.getBus().getBusNumber())
    												  .seatNo(reservationEntity.getSeat().getSeatNo())
    												  .seatNumber(reservationEntity.getSeat().getSeatNumber())
    												  .routeNo(reservationEntity.getRoute().getRouteNo())
    												  .origin(reservationEntity.getRoute().getOrigin())
    												  .destination(reservationEntity.getRoute().getDestination())
    												  .regDate(reservationEntity.getRegDate())
    												  .modDate(reservationEntity.getModDate())
    												  .build();
    	return reservationDTO;
    }
    
}
