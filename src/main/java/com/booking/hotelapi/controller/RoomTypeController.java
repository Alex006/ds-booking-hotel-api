package com.booking.hotelapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
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

import com.booking.hotelapi.entity.RoomType;
import com.booking.hotelapi.entity.assembler.RoomTypeModelAssembler;
import com.booking.hotelapi.exception.ResourceNotFoundException;
import com.booking.hotelapi.service.RoomTypeService;

@RestController
@RequestMapping("/roomTypes")
public class RoomTypeController {

	@Autowired
	RoomTypeService roomTypeService;
	
	@Autowired
	RoomTypeModelAssembler assembler;

	@GetMapping("/")
	public CollectionModel<EntityModel<RoomType>> getRoomTypes() {
		List<EntityModel<RoomType>> roomTypes = roomTypeService.findAll()
													.stream()
													.map(assembler::toModel)
													.collect(Collectors.toList());

		return CollectionModel.of(roomTypes, linkTo(methodOn(RoomTypeController.class).getRoomTypes()).withSelfRel());
	}
	
	@GetMapping("/roomTypes2")
	public List<RoomType> getRoomTypes2() {
		List<RoomType> roomTypes = roomTypeService.findAll()
												   .stream()
												   .collect(Collectors.toList());

		return roomTypes;
	}

	@GetMapping("/{id}")
	public EntityModel<RoomType> findById(@PathVariable("id") Integer id) {
		RoomType foundRoomType = roomTypeService.findById(id);

		if (foundRoomType == null) {
			throw new ResourceNotFoundException("RoomType not found with id=" + id);
		} else {
			return assembler.toModel(foundRoomType);
		}
	}
	
	@GetMapping("/id2/{id}")
	public RoomType findById2(@PathVariable("id") Integer id) {
		RoomType foundRoomType = roomTypeService.findById(id);

		if (foundRoomType == null) {
			throw new ResourceNotFoundException("RoomType not found with id=" + id);
		} else {
			return foundRoomType;
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<RoomType>> update(@RequestBody RoomType newValuesRoomType, @PathVariable Integer id) throws Exception {
		RoomType foundRoomType = roomTypeService.findById(id);
		
		if (foundRoomType == null) {
			throw new Exception("RoomType were not found with id=" + id);
		} else {
			foundRoomType = roomTypeService.update(foundRoomType, newValuesRoomType);
			EntityModel<RoomType> entityModel = assembler.toModel(foundRoomType);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<EntityModel<RoomType>> create(@RequestBody RoomType roomType) throws Exception {
		RoomType createdRoomType = roomTypeService.create(roomType);
		
		if (createdRoomType == null) {
			throw new Exception("RoomType were not created");
		} else {
			EntityModel<RoomType> entityModel = assembler.toModel(createdRoomType);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
}
