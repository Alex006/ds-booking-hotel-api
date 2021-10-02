package com.booking.hotelapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.hotelapi.entity.Reservation;
import com.booking.hotelapi.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}
	
	public Reservation findById(Integer id) {
		return reservationRepository.findByReservationId(id);
	}
	
	public Reservation create(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	public Reservation update(Reservation reservation, Reservation newValuesReservation) {
		reservation.setCustomerBilledObj(newValuesReservation.getCustomerBilledObj());
		reservation.setRoomObj(newValuesReservation.getRoomObj());
		reservation.setStartDate(newValuesReservation.getStartDate());
		reservation.setEndDate(newValuesReservation.getEndDate());
		reservation.setBillingDate(newValuesReservation.getBillingDate());
		reservation.setAmount(newValuesReservation.getAmount());
		
		return reservationRepository.save(reservation);
	}
	
	public boolean isRoomAvailable(Reservation reservation) {
		Integer duplicatedRoomReservations = reservationRepository.checkRoomReserved(reservation.getRoomObj().getRoomId(), reservation.getStartDate(), reservation.getEndDate());
		
		return duplicatedRoomReservations == 0 ? true : false;	
	}
}
