package com.foodDelivery.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

  @ExceptionHandler(BaseFeeNotFoundException.class)
  public ResponseEntity<String> baseFeeNotFound(BaseFeeNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CityNotFoundException.class)
  public ResponseEntity<String> cityNotFound(CityNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConditionNotFoundException.class)
  public ResponseEntity<String> conditionNotFound(ConditionNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(VehicleNotFoundException.class)
  public ResponseEntity<String> vehicleNotFound(VehicleNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(WeatherDataNotFoundException.class)
  public ResponseEntity<String> weatherDataNotFound(WeatherDataNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(WeatherDataReadingException.class)
  public ResponseEntity<String> weatherDataReadingException(WeatherDataReadingException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ParseObservationTimeException.class)
  public ResponseEntity<String> parseObservationTimeException(ParseObservationTimeException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
