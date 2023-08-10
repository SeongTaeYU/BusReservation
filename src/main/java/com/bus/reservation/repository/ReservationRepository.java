package com.bus.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
