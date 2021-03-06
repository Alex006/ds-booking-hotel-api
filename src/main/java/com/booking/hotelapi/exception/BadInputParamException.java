package com.booking.hotelapi.exception;

import lombok.Setter;

@Setter
public class BadInputParamException extends RuntimeException {
    private String message;

    public BadInputParamException(String message){
        super(message);
        this.message = message;
    }

}