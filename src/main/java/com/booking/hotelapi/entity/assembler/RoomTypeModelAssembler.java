package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.RoomTypeController;
import com.booking.hotelapi.entity.RoomType;

@Component
public class RoomTypeModelAssembler implements RepresentationModelAssembler<RoomType, EntityModel<RoomType>> {
	@Override
	public EntityModel<RoomType> toModel(RoomType roomType) {

		return EntityModel.of(roomType, linkTo(methodOn(RoomTypeController.class).findById(roomType.getRoomTypeId())).withSelfRel(),
				linkTo(methodOn(RoomTypeController.class).getRoomTypes()).withRel("roomTypes"));
	}
}
