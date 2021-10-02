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

import com.booking.hotelapi.entity.Customer;
import com.booking.hotelapi.entity.assembler.CustomerModelAssembler;
import com.booking.hotelapi.exception.ResourceNotFoundException;
import com.booking.hotelapi.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerModelAssembler assembler;

	@GetMapping("/")
	public CollectionModel<EntityModel<Customer>> getCustomers() {
		List<EntityModel<Customer>> customers = customerService.findAll()
													.stream()
													.map(assembler::toModel)
													.collect(Collectors.toList());

		return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
	}

	@GetMapping("/customer2")
	public List<Customer> getCustomers2() {
		List<Customer> customers = customerService.findAll()
												  .stream()
												  .collect(Collectors.toList());

		return customers;
	}
	
	@GetMapping("/{id}")
	public EntityModel<Customer> findById(@PathVariable("id") Integer id) {
		Customer foundCustomer = customerService.findById(id);

		if (foundCustomer == null) {
			throw new ResourceNotFoundException("Customer not found with id=" + id);
		} else {
			return assembler.toModel(foundCustomer);
		}
	}
	
	@GetMapping("/id2/{id}")
	public Customer findById2(@PathVariable("id") Integer id) {
		Customer foundCustomer = customerService.findById(id);

		if (foundCustomer == null) {
			throw new ResourceNotFoundException("Customer not found with id=" + id);
		} else {
			return foundCustomer;
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Customer>> update(@RequestBody Customer newValuesCustomer, @PathVariable Integer id) throws Exception {
		Customer foundCustomer = customerService.findById(id);
		
		if (foundCustomer == null) {
			throw new Exception("Customer were not found with id=" + id);
		} else {
			foundCustomer = customerService.update(foundCustomer, newValuesCustomer);
			EntityModel<Customer> entityModel = assembler.toModel(foundCustomer);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<EntityModel<Customer>> create(@RequestBody Customer customer) throws Exception {
		Customer createdCustomer = customerService.create(customer);
		
		if (createdCustomer == null) {
			throw new Exception("Customer were not created");
		} else {
			EntityModel<Customer> entityModel = assembler.toModel(createdCustomer);
			
			return ResponseEntity
				      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				      .body(entityModel);
		}
	}

}
