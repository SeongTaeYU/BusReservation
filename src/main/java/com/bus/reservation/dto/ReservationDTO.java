package com.bus.reservation.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
	
	private Integer reservationNo;
	
    private Date reservationTime;
	
    private Integer userNo;
    private String busName;
	
    private Integer busNo;
    private String busNumber;
	
    private Integer seatNo;
    private String seatNumber;
	
    private Integer routeNo;
    private String origin;
    private String destination;
    
    private LocalDateTime regDate;
	private LocalDateTime modDate;

	    
}
