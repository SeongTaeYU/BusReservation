package com.bus.reservation.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@DynamicInsert
@Table(name = "tbl_route")
public class Route extends BaseEntityWithTimeStamps{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer routeNo;
	
	@Column(nullable = false)
	private String origin;
	
	@Column(nullable = false)
	private String destination;
	
	@Column(nullable = false)
	private Date departureTime;
	
	@Column(nullable = false)
	private Date arrivalTime;
	
	@Column(nullable = false)
	private Date date;
}
