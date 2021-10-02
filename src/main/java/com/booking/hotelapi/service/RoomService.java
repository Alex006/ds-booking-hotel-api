package com.booking.hotelapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.hotelapi.entity.Room;
import com.booking.hotelapi.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepository;

	public List<Room> findAll() {
		return roomRepository.findAll();
	}
	
	public Room findById(Integer id) {
		return roomRepository.findByRoomId(id);
	}
	
	public Room create(Room room) {
		return roomRepository.save(room);
	}
	
	public Room update(Room room, Room newValuesRoom) {
		room.setDescription(newValuesRoom.getDescription());
		room.setRoomNumber(newValuesRoom.getRoomNumber());
		room.setFloorNumber(newValuesRoom.getFloorNumber());
		room.setPrice(newValuesRoom.getPrice());
		
		return roomRepository.save(room);
	}
	
}
