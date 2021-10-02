package com.booking.hotelapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.hotelapi.entity.Availability;
import com.booking.hotelapi.entity.Reservation;
import com.booking.hotelapi.entity.Room;
import com.booking.hotelapi.entity.assembler.AvailabilityModelAssembler;
import com.booking.hotelapi.entity.assembler.ReservationModelAssembler;
import com.booking.hotelapi.exception.BadInputParamException;
import com.booking.hotelapi.exception.ResourceNotFoundException;
import com.booking.hotelapi.service.ReservationService;
import com.booking.hotelapi.service.RoomService;
import com.booking.hotelapi.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@Autowired
	ReservationModelAssembler assembler;
	
	@Autowired
	AvailabilityModelAssembler availabilityAssembler;
	
	@Autowired
	RoomService roomService;
	
	@GetMapping("/")
	public CollectionModel<EntityModel<Reservation>> getReservations() {
		List<EntityModel<Reservation>> reservations = reservationService.findAll()
													.stream()
													.map(assembler::toModel)
													.collect(Collectors.toList());

		return CollectionModel.of(reservations, linkTo(methodOn(ReservationController.class).getReservations()).withSelfRel());
	}
	
	@GetMapping("/reservations2")
	public List<Reservation> getReservations2() {
		List<Reservation> reservations = reservationService.findAll()
													.stream()
													.collect(Collectors.toList());

		return reservations;
	}

	@GetMapping("/searchAvailability")
	public CollectionModel<EntityModel<Availability>> searchAvailabilities() {
		List<Reservation> reservations = reservationService.findAll()
														   .stream()
														   .collect(Collectors.toList());
		
		List<EntityModel<Availability>> availabilities = getAvailabilities(reservations)
															.stream()
															.map(availabilityAssembler::toModel)
															.collect(Collectors.toList());

		return CollectionModel.of(availabilities, linkTo(methodOn(ReservationController.class).searchAvailabilities()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<Reservation> findById(@PathVariable("id") Integer id) {
		Reservation foundReservation = reservationService.findById(id);

		if (foundReservation == null) {
			throw new ResourceNotFoundException("Reservation not found with id=" + id);
		} else {
			return assembler.toModel(foundReservation);
		}
	}
	
	@GetMapping("/id2/{id}")
	public Reservation findById2(@PathVariable("id") Integer id) {
		Reservation foundReservation = reservationService.findById(id);

		if (foundReservation == null) {
			throw new ResourceNotFoundException("Reservation not found with id=" + id);
		} else {
			return foundReservation;
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Reservation>> update(@RequestBody Reservation newValuesReservation, @PathVariable Integer id) throws Exception {
		Reservation foundReservation = reservationService.findById(id);
		
		if (foundReservation == null) {
			throw new Exception("Reservation were not found with id=" + id);
		} else {
			foundReservation = reservationService.update(foundReservation, newValuesReservation);
			EntityModel<Reservation> entityModel = assembler.toModel(foundReservation);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<EntityModel<Reservation>> create(@RequestBody Reservation reservation) throws Exception {
		
		validateRequest(reservation);
		
		//setting calculated properties of the reservation
		int daysQuantity = (int) ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
		
		Double roomPricePerDay = roomService.findById(reservation.getRoomObj().getRoomId()).getPrice();
		reservation.setBillingDate(LocalDateTime.now());
		reservation.setAmount(daysQuantity * roomPricePerDay);
		
		Reservation createdReservation = reservationService.create(reservation);
		
		if (createdReservation == null) {
			throw new Exception("Reservation were not created");
		} else {
			EntityModel<Reservation> entityModel = assembler.toModel(createdReservation);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}

	
	private void validateRequest(Reservation reservation) {
		//***All reservations start at least the next day of booking***
		int daysAheadOfReservation = (int) ChronoUnit.DAYS.between(LocalDate.now(), reservation.getStartDate());
		if(daysAheadOfReservation >= Constants.MIN_DAYS_AHEAD_FOR_RESERVATION) {
			
			//***To give a chance to everyone to book the room, the stay can’t be longer than 3 days and can’t be reserved more than 30 days in advance.***
			if(daysAheadOfReservation < Constants.MAX_DAYS_AHEAD_FOR_RESERVATION) {
				int daysOfReservations = (int) ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
				if((daysOfReservations > 0) && (daysOfReservations <= Constants.MAX_DAYS_OF_RESERVATIONS)) {
					
					if(isRoomAvailable(reservation)) {
						log.info("***Request validation is ok***");
					}else {
						throw new BadInputParamException("The room is already in use for those days. Please check availability with searchAvailabilities request");
					}
					
				}else {
					throw new BadInputParamException("Reservation must not exceed " + Constants.MAX_DAYS_OF_RESERVATIONS + " days of reservations.");
				}
			}else {
				throw new BadInputParamException("Reservation must be done no more than " + Constants.MAX_DAYS_AHEAD_FOR_RESERVATION + " days in advance of the start date.");
			}
		}else {
			throw new BadInputParamException("Reservation must be done " + Constants.MIN_DAYS_AHEAD_FOR_RESERVATION + " days in advance of the start date.");
		}
	}

	private boolean isRoomAvailable(Reservation reservation) {
		return reservationService.isRoomAvailable(reservation);
	}

	private List<Availability> getAvailabilities(List<Reservation> reservations){
		List<Availability> availabilities = new ArrayList<Availability>();
		
		Hashtable<Room, List<Availability>> roomAvailabilities = getAvailabilitiesMap(reservations);
		
		for(Room room : roomAvailabilities.keySet()) {
			availabilities.addAll(roomAvailabilities.get(room));
		}
		
		log.info("Room availabilities=" + availabilities);
		
		return availabilities;
	}
	
	private Hashtable<Room, List<Availability>> getAvailabilitiesMap(List<Reservation> reservations){
		
		reservations.forEach(obj -> log.info("StartDates before sort: "
				+ "Room= " + obj.getRoomObj().getRoomId() + ". StartDate=" + obj.getStartDate() + ". EndDate=" +obj.getEndDate()));
		
		//sorting reservation list by StartDate
		reservations.sort((obj1, obj2) -> obj1.getStartDate().compareTo(obj2.getStartDate()));
		
		reservations.forEach(obj -> log.info("StartDates after sort: "
											+ "Room= " + obj.getRoomObj().getRoomId() + ". StartDate=" + obj.getStartDate() + ". EndDate=" +obj.getEndDate()));
		
		//grouping reservations by room
		Map<Room, List<Reservation>> roomReservations = reservations.stream().collect(Collectors.groupingBy(Reservation::getRoomObj));
		
		Hashtable<Room, List<Availability>> roomAvailabilities = new Hashtable<Room, List<Availability>>();
		
		//getting room availabilities
		for(Room room : roomReservations.keySet()) {
			 
			List<Reservation> rReservations = roomReservations.get(room);
			List<Availability> rAvailabilities = new ArrayList<Availability>();
			
			for(int i = 0; i < rReservations.size(); i++) {
				Reservation currentReservation = rReservations.get(i);
				
				if(i + 1 < rReservations.size()) {
					Reservation nextReservation = rReservations.get(i + 1);
					
					int resevationDiffDays = (int) ChronoUnit.DAYS.between(nextReservation.getStartDate(), currentReservation.getEndDate());
					resevationDiffDays = Math.abs(resevationDiffDays);
					
					log.info("Room " + room.getRoomId() + ". currentReservation.getEndDate()=" + currentReservation.getEndDate() + ". nextReservation.getStartDate()" + nextReservation.getStartDate() );
					
					//***All reservations start at least the next day of booking***
					if(resevationDiffDays > Constants.MIN_DAYS_AHEAD_FOR_RESERVATION) {
						
						/* ***To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.***
						 * Availability start next day of currentReservation.getEndDate() and 
						 * ends 1 day before of nextReservation.getStartDate() */
						Availability availability = new Availability(room
																	, currentReservation.getEndDate().plusDays(1)
																	, nextReservation.getStartDate().minusDays(1));
						
						rAvailabilities.add(availability);
						log.info("Room " + room.getRoomId() + " has availability: " + availability);
						
					}else {
						log.info("Room " + room.getRoomId() + " doesn´t have enough ahead days: " + resevationDiffDays);
					}
					
				}else {
					//* If there is not more reservations for this room, evaluate to add 1 more availability:
					//* Add the availability if currentReservation.getEndDate() is before the MAX 30 days the user has as limit to do a reservation
					int lastEndDayReservationDiffDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), currentReservation.getEndDate());
					
					log.info("Room " + room.getRoomId() + ". currentReservation.getEndDate()=" + currentReservation.getEndDate() 
							+ " and no more nextReservation. lastEndDayReservationDiffDays=" + lastEndDayReservationDiffDays);
					
					if(lastEndDayReservationDiffDays < Constants.MAX_DAYS_AHEAD_FOR_RESERVATION) {
						Availability availability = new Availability(room, currentReservation.getEndDate().plusDays(1), LocalDate.now().plusDays(Constants.MAX_DAYS_AHEAD_FOR_RESERVATION));
						rAvailabilities.add(availability);
						log.info("Room " + room.getRoomId() + " has availability: " + availability);
					}
				}
			}
			
			roomAvailabilities.put(room, rAvailabilities);
		}
		
		return roomAvailabilities;
	}
}
