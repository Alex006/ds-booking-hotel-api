package com.booking.hotelapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.hotelapi.entity.BedType;
import com.booking.hotelapi.entity.RoomType;
import com.booking.hotelapi.repository.BedTypeRepository;
import com.booking.hotelapi.repository.RoomTypeRepository;

@Service
public class BedTypeService {

	@Autowired
	BedTypeRepository bedTypeRepository;

	public List<BedType> findAll() {
		return bedTypeRepository.findAll();
	}
	
	public BedType findById(Integer id) {
		return bedTypeRepository.findByBedTypeId(id);
	}
	
	public BedType create(BedType bedType) {
		return bedTypeRepository.save(bedType);
	}
	
	public BedType update(BedType bedType, BedType newValuesBedType) {
		bedType.setName(newValuesBedType.getName());
		bedType.setSize(newValuesBedType.getSize());
		
		return bedTypeRepository.save(bedType);
	}
	
}
