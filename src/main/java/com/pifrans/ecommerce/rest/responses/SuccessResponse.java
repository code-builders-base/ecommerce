package com.pifrans.ecommerce.rest.responses;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse<T> {


    public ResponseEntity<T> handle(T object, Class<?> classController, HttpServletRequest request, HttpStatus status) {
        return new ResponseEntity<>(object, status);
    }
}
