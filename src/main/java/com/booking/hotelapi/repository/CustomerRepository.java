package com.booking.hotelapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {	
	Customer findByCustomerId(Integer id);
}
