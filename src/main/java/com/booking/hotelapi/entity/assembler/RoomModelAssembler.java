package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.RoomController;
import com.booking.hotelapi.entity.Room;

@Component
public class RoomModelAssembler implements RepresentationModelAssembler<Room, EntityModel<Room>> {
	@Override
	public EntityModel<Room> toModel(Room room) {

		return EntityModel.of(room, linkTo(methodOn(RoomController.class).findById(room.getRoomId())).withSelfRel(),
				linkTo(methodOn(RoomController.class).getRooms()).withRel("rooms"));
	}
}
