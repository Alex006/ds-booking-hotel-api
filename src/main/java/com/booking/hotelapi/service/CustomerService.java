package com.booking.hotelapi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.hotelapi.entity.Customer;
import com.booking.hotelapi.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	public Customer findById(Integer id) {
		return customerRepository.findByCustomerId(id);
	}
	
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer update(Customer customer, Customer newValuesCustomer) {
		customer.setFirstName(newValuesCustomer.getFirstName());
		customer.setLastName(newValuesCustomer.getLastName());
		customer.setBirthDate(newValuesCustomer.getBirthDate());
		customer.setEmail(newValuesCustomer.getEmail());
		customer.setPhoneNumber(newValuesCustomer.getPhoneNumber());
		
		return customerRepository.save(customer);
	}
	
}
