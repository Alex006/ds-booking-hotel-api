package com.booking.hotelapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.hotelapi.entity.RoomType;
import com.booking.hotelapi.repository.RoomTypeRepository;

@Service
public class RoomTypeService {

	@Autowired
	RoomTypeRepository roomTypeRepository;

	public List<RoomType> findAll() {
		return roomTypeRepository.findAll();
	}
	
	public RoomType findById(Integer id) {
		return roomTypeRepository.findByRoomTypeId(id);
	}
	
	public RoomType create(RoomType roomType) {
		return roomTypeRepository.save(roomType);
	}
	
	public RoomType update(RoomType roomType, RoomType newValuesRoomType) {
		roomType.setName(newValuesRoomType.getName());
		roomType.setDescription(newValuesRoomType.getDescription());
		
		return roomTypeRepository.save(roomType);
	}
	
}
