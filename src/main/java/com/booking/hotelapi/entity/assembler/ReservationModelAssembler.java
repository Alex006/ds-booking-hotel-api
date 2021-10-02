package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.ReservationController;
import com.booking.hotelapi.entity.Reservation;

@Component
public class ReservationModelAssembler implements RepresentationModelAssembler<Reservation, EntityModel<Reservation>> {
	@Override
	public EntityModel<Reservation> toModel(Reservation reservation) {

		return EntityModel.of(reservation, linkTo(methodOn(ReservationController.class).findById(reservation.getReservationId())).withSelfRel(),
				linkTo(methodOn(ReservationController.class).getReservations()).withRel("reservations"));
	}
}
