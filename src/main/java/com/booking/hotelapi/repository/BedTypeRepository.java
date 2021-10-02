package com.booking.hotelapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.BedType;

@Repository
public interface BedTypeRepository extends JpaRepository<BedType, Long> {
	BedType findByBedTypeId(Integer id);
}
