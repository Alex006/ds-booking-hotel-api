package com.booking.hotelapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByRoomId(Integer id);
}
