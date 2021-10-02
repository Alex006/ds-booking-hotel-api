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

import com.booking.hotelapi.entity.Room;
import com.booking.hotelapi.entity.assembler.RoomModelAssembler;
import com.booking.hotelapi.exception.ResourceNotFoundException;
import com.booking.hotelapi.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	RoomService roomService;
	
	@Autowired
	RoomModelAssembler assembler;

	@GetMapping("/")
	public CollectionModel<EntityModel<Room>> getRooms() {
		List<EntityModel<Room>> rooms = roomService.findAll()
													.stream()
													.map(assembler::toModel)
													.collect(Collectors.toList());

		return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).getRooms()).withSelfRel());
	}
	

	@GetMapping("/rooms2")
	public List<Room> getRooms2() {
		List<Room> rooms = roomService.findAll()
									  .stream()
									  .collect(Collectors.toList());

		return rooms;
	}
	
	@GetMapping("/{id}")
	public EntityModel<Room> findById(@PathVariable("id") Integer id) {
		Room foundRoom = roomService.findById(id);

		if (foundRoom == null) {
			throw new ResourceNotFoundException("Room not found with id=" + id);
		} else {
			return assembler.toModel(foundRoom);
		}
	}
	
	@GetMapping("/id2/{id}")
	public Room findById2(@PathVariable("id") Integer id) {
		Room foundRoom = roomService.findById(id);

		if (foundRoom == null) {
			throw new ResourceNotFoundException("Room not found with id=" + id);
		} else {
			return foundRoom;
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Room>> update(@RequestBody Room newValuesRoom, @PathVariable Integer id) throws Exception {
		Room foundRoom = roomService.findById(id);
		
		if (foundRoom == null) {
			throw new Exception("Room were not found with id=" + id);
		} else {
			foundRoom = roomService.update(foundRoom, newValuesRoom);
			EntityModel<Room> entityModel = assembler.toModel(foundRoom);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<EntityModel<Room>> create(@RequestBody Room room) throws Exception {
		Room createdRoom = roomService.create(room);
		
		if (createdRoom == null) {
			throw new Exception("Room were not created");
		} else {
			EntityModel<Room> entityModel = assembler.toModel(createdRoom);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
}
