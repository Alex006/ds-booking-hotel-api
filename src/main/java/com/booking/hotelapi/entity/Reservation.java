package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "RESERVATIONS")
public class Reservation implements Serializable{
	
	private static final long serialVersionUID = 5434713774530016814L;
	
	//default constructor
	Reservation(){}
	
	Reservation(Customer customer, Room room, LocalDate startDate
			, LocalDate endDate, LocalDateTime billingDate, Double amount){
		this.customerBilledObj = customer;
		this.roomObj = room;
		this.startDate = startDate;
		this.endDate = endDate;
		this.billingDate = billingDate;
		this.amount = amount;
	}
	
	@Id
	@Column(name="RESERVATION_ID", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_BILLED_ID", nullable = false)
	@NotNull(message = "Customer is required")
	private Customer customerBilledObj;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", nullable = false)
	@NotNull(message = "Room is required")
	private Room roomObj;
	
	@Column(name="START_DATE", updatable=true)
	@NotNull(message = "Start date is required")
	private LocalDate startDate; //***To simplify the use case, a �DAY� in the hotel room starts from 00:00 to 23:59:59.***
	
	@Column(name="END_DATE", updatable=true)
	@NotNull(message = "End date is required")
	private LocalDate endDate; //***To simplify the use case, a �DAY� in the hotel room starts from 00:00 to 23:59:59.***
	
	@Column(name="BILLING_DATE", updatable=true)
	//@NotNull(message = "Billing date is required") billingDate is calculated, required just for database
	private LocalDateTime billingDate;
	
	@Column(name="AMOUNT", updatable=true)
	//@NotNull(message = "Amount is required") Amount is calculated, required just for database
	private Double amount;
	
}
