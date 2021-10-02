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

import com.booking.hotelapi.entity.BedType;
import com.booking.hotelapi.entity.assembler.BedTypeModelAssembler;
import com.booking.hotelapi.exception.ResourceNotFoundException;
import com.booking.hotelapi.service.BedTypeService;

@RestController
@RequestMapping("/bedTypes")
public class BedTypeController {

	@Autowired
	BedTypeService bedTypeService;
	
	@Autowired
	BedTypeModelAssembler assembler;

	@GetMapping("/")
	public CollectionModel<EntityModel<BedType>> getBedTypes() {
		List<EntityModel<BedType>> bedTypes = bedTypeService.findAll()
													.stream()
													.map(assembler::toModel)
													.collect(Collectors.toList());

		return CollectionModel.of(bedTypes, linkTo(methodOn(BedTypeController.class).getBedTypes()).withSelfRel());
	}
	
	@GetMapping("/bedTypes2")
	public List<BedType> getBedTypes2() {
		List<BedType> bedTypes = bedTypeService.findAll()
												.stream()
												.collect(Collectors.toList());

		return bedTypes;
	}

	@GetMapping("/{id}")
	public EntityModel<BedType> findById(@PathVariable("id") Integer id) {
		BedType foundBedType = bedTypeService.findById(id);

		if (foundBedType == null) {
			throw new ResourceNotFoundException("BedType not found with id=" + id);
		} else {
			return assembler.toModel(foundBedType);
		}
	}
	
	@GetMapping("/id2/{id}")
	public BedType findById2(@PathVariable("id") Integer id) {
		BedType foundBedType = bedTypeService.findById(id);

		if (foundBedType == null) {
			throw new ResourceNotFoundException("BedType not found with id=" + id);
		} else {
			return foundBedType;
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<BedType>> update(@RequestBody BedType newValuesBedType, @PathVariable Integer id) throws Exception {
		BedType foundBedType = bedTypeService.findById(id);
		
		if (foundBedType == null) {
			throw new Exception("BedType were not found with id=" + id);
		} else {
			foundBedType = bedTypeService.update(foundBedType, newValuesBedType);
			EntityModel<BedType> entityModel = assembler.toModel(foundBedType);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<EntityModel<BedType>> create(@RequestBody BedType bedType) throws Exception {
		BedType createdBedType = bedTypeService.create(bedType);
		
		if (createdBedType == null) {
			throw new Exception("BedType were not created");
		} else {
			EntityModel<BedType> entityModel = assembler.toModel(createdBedType);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
}
