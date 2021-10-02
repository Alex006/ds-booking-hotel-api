package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Availability extends Reservation implements Serializable{

	private static final long serialVersionUID = 5934713774530016814L;
	
	public Availability(Room room, LocalDate startDate, LocalDate endDate) {
		super(null, room, startDate, endDate, null, null);
	}
	
}
