package com.booking.hotelapi.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	Reservation findByReservationId(Integer id);
	
	@Query(value = "SELECT count(*) FROM reservations WHERE ROOM_ID = :roomId AND ((START_DATE >= :startDate AND :startDate <= END_DATE) OR (START_DATE >= :endDate AND :endDate <= END_DATE))", nativeQuery = true)
	Integer checkRoomReserved(@Param("roomId") Integer roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
