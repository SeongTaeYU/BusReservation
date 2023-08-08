package com.bus.reservation.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@DynamicInsert
@Table(name = "tbl_reservation")
public class Reservation extends BaseEntityWithTimeStamps {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationNo;
	
	@Column(nullable = false)
    private Date reservationTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bus_no")
    private Bus bus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_no")
    private Seat seat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_no")
    private Route route;

	    
}
