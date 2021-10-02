package com.booking.hotelapi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ROOM_TYPES")
public class RoomType implements Serializable{
	
	private static final long serialVersionUID = 5433713774530016813L;
	
	@Id
	@Column(name="ROOM_TYPE_ID", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomTypeId;
	
	@Column(name="NAME", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "Name is required")
	private String name;
	
	@Column(name="DESCRIPTION", updatable=true)
	@Length(max = 45, min = 0)
	@NotNull(message = "Description is required")
	private String description;
	
	@Transient
	@JsonIgnore
	@OneToMany(mappedBy = "roomTypeObj")
	private List<Room> rooms;
}
