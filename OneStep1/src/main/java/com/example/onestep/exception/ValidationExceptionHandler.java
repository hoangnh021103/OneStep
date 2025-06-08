package com.example.onestep.exception;

import com.example.onestep.dto.responerror.ErrorDetails;
import com.example.onestep.dto.responerror.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ErrorDetails(err.getField(), err.getDefaultMessage()))
                .toList();

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errorDetails
        );
        return ResponseEntity.badRequest().body(response);
    }

}
