package com.bus.reservation.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RouteDTO {
	
	private Integer routeNo;
	
	private String origin;
	
	private String destination;
	
	private Date departureTime;
	
	private Date arrivalTime;
	
	private Date date;
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
