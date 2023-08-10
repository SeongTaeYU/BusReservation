package com.bus.reservation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@DynamicInsert
@Table(name = "tbl_bus")
public class Bus extends BaseEntityWithTimeStamps {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busNo;
	
	@Column(nullable = false)
    private String busNumber;
	
	@Column(nullable = false)
    private String busDriver;
	
	@Column(nullable = false)
    private String busCompany;
	
	@Column(nullable = false)
	@ColumnDefault("0")
    private Integer seatCount;


	
}
