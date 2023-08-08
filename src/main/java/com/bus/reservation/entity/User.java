package com.bus.reservation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@EqualsAndHashCode(callSuper = false)
@Entity
@DynamicInsert
@Table(name = "tbl_user")
public class User extends BaseEntityWithTimeStamps{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userNo;
	
	@Column(nullable = false)
    private String userName;
	
	@Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
	
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String address;
    
}
