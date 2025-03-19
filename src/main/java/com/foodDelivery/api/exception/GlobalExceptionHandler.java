package com.foodDelivery.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler that catches exceptions thrown by controllers
 * and returns a structured ErrorResponse with appropriate HTTP status.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

  /**
   * Handles exceptions when BaseFee data is not found.
   *
   * @param ex the BaseFeeNotFoundException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 404.
   */
  @ExceptionHandler(BaseFeeNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBaseFeeNotFoundException(BaseFeeNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Resource not found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions when a City data is not found.
   *
   * @param ex the CityNotFoundException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 404.
   */
  @ExceptionHandler(CityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCityNotFoundException(CityNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Resource not found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions when Conditions data is not found.
   *
   * @param ex the ConditionNotFoundException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 404.
   */
  @ExceptionHandler(ConditionNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleConditionNotFoundException(ConditionNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT.value(), "Resource not found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions when Vehicle data is not found.
   *
   * @param ex the VehicleNotFoundException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 404.
   */
  @ExceptionHandler(VehicleNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleVehicleNotFoundException(VehicleNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT.value(), "Resource not found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions when WeatherData is not found.
   *
   * @param ex the WeatherDataNotFoundException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 404.
   */
  @ExceptionHandler(WeatherDataNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleWeatherDataNotFoundException(WeatherDataNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT.value(), "Resource not found", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles exceptions that occur while reading the weather data from the API.
   *
   * @param ex the WeatherDataReadingException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 500.
   */
  @ExceptionHandler(WeatherDataReadingException.class)
  public ResponseEntity<ErrorResponse> handleWeatherDataReadingException(WeatherDataReadingException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Reading weather data failed", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles exceptions that occur during observation time parsing to human-readable format.
   *
   * @param ex the ParseObservationTimeException that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 400.
   */
  @ExceptionHandler(ParseObservationTimeException.class)
  public ResponseEntity<ErrorResponse> handleParseObservationTimeException(ParseObservationTimeException ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Incorrect request", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Fallback for unhandled exceptions.
   *
   * @param ex the Exception that is thrown.
   * @return a ResponseEntity containing the error response with HTTP status 500.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
