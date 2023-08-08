package com.bus.reservation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeatDTO {
	
    private Integer seatNo;
	
    private String seatNumber;
	
    private Boolean isReserved;
	
    private Integer busNo;
    private String busNumber;
    
	private LocalDateTime regDate;
	private LocalDateTime modDate;
    
}