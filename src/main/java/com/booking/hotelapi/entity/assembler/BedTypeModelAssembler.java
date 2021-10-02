package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.BedTypeController;
import com.booking.hotelapi.entity.BedType;

@Component
public class BedTypeModelAssembler implements RepresentationModelAssembler<BedType, EntityModel<BedType>> {
	@Override
	public EntityModel<BedType> toModel(BedType bedType) {

		return EntityModel.of(bedType, linkTo(methodOn(BedTypeController.class).findById(bedType.getBedTypeId())).withSelfRel(),
				linkTo(methodOn(BedTypeController.class).getBedTypes()).withRel("bedTypes"));
	}
}
