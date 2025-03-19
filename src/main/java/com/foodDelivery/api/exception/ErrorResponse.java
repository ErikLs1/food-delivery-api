package com.foodDelivery.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Error response object that is returned by the GlobalExceptionHandler.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * Time when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * The status of the error.
     */
    private Integer status;

    /**
     * The name of the error.
     */
    private String error;

    /**
     * The detailer message of the error.
     */
    private String message;
}
