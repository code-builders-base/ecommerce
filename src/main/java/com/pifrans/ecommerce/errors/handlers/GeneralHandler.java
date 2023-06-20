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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GeneralHandler {
    private final HttpServletRequest request;


    @Autowired
    public GeneralHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<CommonErroDto>> handler(NotFoundException exception) {
        var commonErroDto = new CommonErroDto(exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(List.of(commonErroDto), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CommonErroDto>> handler(MethodArgumentNotValidException exception) {
        var commonErroDtos = new ArrayList<CommonErroDto>();
        var errors = exception.getBindingResult().getFieldErrors();

        errors.forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            var field = error.getField();
            var finalMessage = String.format("Campo (%s), mensagem (%s)!", field, errorMessage);
            commonErroDtos.add(new CommonErroDto(finalMessage, request.getRequestURI()));
        });
        return new ResponseEntity<>(commonErroDtos, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<CommonErroDto>> handler(DataIntegrityViolationException exception) {
        var commonErroDto = new CommonErroDto(Objects.requireNonNull(exception.getRootCause()).getMessage(), request.getRequestURI());
        return new ResponseEntity<>(List.of(commonErroDto), HttpStatus.CONFLICT);
    }
}
