package com.foodDelivery.api.exception;

/**
 * Exception is thrown when reading or processing weather data from the API failed.
 */
public class WeatherDataReadingException extends RuntimeException {

    /**
     * A new WeatherDataReadingException with the specified detail message.
     *
     * @param message the detail message.
     * @param cause the cause of the exception
     */
    public WeatherDataReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * A new WeatherDataReadingException with the specified detail message.
     *
     * @param message the detail message.
     */
    public WeatherDataReadingException(String message) {
        super(message);
    }
}
