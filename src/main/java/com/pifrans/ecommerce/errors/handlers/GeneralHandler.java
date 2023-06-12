package com.pifrans.ecommerce.errors.handlers;

import com.pifrans.ecommerce.domains.dtos.CommonErroDto;
import com.pifrans.ecommerce.errors.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GeneralHandler {
    private final HttpServletRequest request;


    @Autowired
    public GeneralHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonErroDto> handler(NotFoundException exception) {
        var commonErroDto = new CommonErroDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(commonErroDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErroDto> handler(MethodArgumentNotValidException exception) {
        var commonErroDto = new CommonErroDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(commonErroDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonErroDto> handler(DataIntegrityViolationException exception) {
        var commonErroDto = new CommonErroDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), Objects.requireNonNull(exception.getRootCause()).getMessage(), request.getRequestURI());
        return new ResponseEntity<>(commonErroDto, HttpStatus.CONFLICT);
    }
}
