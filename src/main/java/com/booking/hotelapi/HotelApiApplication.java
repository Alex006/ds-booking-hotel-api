package com.booking.hotelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Booking Hotel API", version = "1.0", description = "Booking Information"))
public class HotelApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HotelApiApplication.class, args);
	}
}
