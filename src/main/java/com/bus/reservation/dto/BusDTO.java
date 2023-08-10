package com.bus.reservation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusDTO {
	
    private Integer busNo;
	
    private String busNumber;
	
    private String busDriver;
	
    private String busCompany;
	
    private String seatCount;
    
    private LocalDateTime regDate;
	private LocalDateTime modDate;
	
}
