package com.booking.hotelapi.entity.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.booking.hotelapi.controller.CustomerController;
import com.booking.hotelapi.entity.Customer;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
	@Override
	public EntityModel<Customer> toModel(Customer customer) {

		return EntityModel.of(customer, linkTo(methodOn(CustomerController.class).findById(customer.getCustomerId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));
	}
}
