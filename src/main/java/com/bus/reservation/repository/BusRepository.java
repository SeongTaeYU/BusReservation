package com.bus.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.reservation.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {

}
