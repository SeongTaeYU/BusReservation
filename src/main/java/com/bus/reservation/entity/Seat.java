package com.bus.reservation.entity;

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
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
@Entity
@DynamicInsert
@Table(name = "tbl_seat")
public class Seat extends BaseEntityWithTimeStamps{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatNo;
	
	@Column(nullable = false)
    private String seatNumber;
	
	@Column(nullable = false)
    private Boolean isReserved;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bus_no")
    private Bus bus;
    
}