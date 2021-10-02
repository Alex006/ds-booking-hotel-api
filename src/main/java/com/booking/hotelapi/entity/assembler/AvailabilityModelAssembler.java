package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.ReservationController;
import com.booking.hotelapi.entity.Availability;

@Component
public class AvailabilityModelAssembler implements RepresentationModelAssembler<Availability, EntityModel<Availability>> {
	@Override
	public EntityModel<Availability> toModel(Availability availability) {

		EntityModel<Availability> entityModel = null;
		try {
			entityModel = EntityModel.of(availability, linkTo(methodOn(ReservationController.class).create(availability)).withSelfRel(),
					linkTo(methodOn(ReservationController.class).searchAvailabilities()).withRel("reservations"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entityModel;
	}
}
