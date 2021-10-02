package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ROOMS")
public class Room implements Serializable{
	
	private static final long serialVersionUID = 5433713774530016814L;
	
	@Id
	@Column(name="ROOM_ID", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomId;
	
	@Column(name="DESCRIPTION", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "Description is required")
	private String description;

	@Column(name="ROOM_NUMBER", updatable=true, length=2)
	@NotNull(message = "Room number is required")
	private Integer roomNumber;
	
	@Column(name="FLOOR_NUMBER", updatable=true, length=1)
	@NotNull(message = "Floor number is required")
	private Integer floorNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_TYPE_ID", nullable = false)
	@NotNull(message = "RoomType is required")
	private RoomType roomTypeObj;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BED_TYPE_ID", nullable = false)
	@NotNull(message = "BedType is required")
	private BedType bedTypeObj;
	
	@Column(name="PRICE", updatable=true)
	@NotNull(message = "Price is required")
	private Double price;
	
	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "roomObj")
	private List<Reservation> reservations;
	
}
