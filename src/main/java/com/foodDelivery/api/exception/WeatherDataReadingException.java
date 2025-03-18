package com.foodDelivery.api.exception;

public class WeatherDataReadingException extends RuntimeException {
    public WeatherDataReadingException(String message, Throwable cause) {
        super(message, cause);
    }
    public WeatherDataReadingException(String message) {
        super(message);
    }
}
