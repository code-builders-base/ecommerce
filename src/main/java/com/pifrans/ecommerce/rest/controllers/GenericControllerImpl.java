package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.constants.ReflectMethods;
import com.pifrans.ecommerce.rest.responses.SuccessResponse;
import com.pifrans.ecommerce.services.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public abstract class GenericControllerImpl<T, C> implements GenericController<T> {
    private final GenericService<T> service;
    private final Class<T> classModel;
    private final Class<C> classController;
    private final HttpServletRequest request;


    @Autowired
    protected GenericControllerImpl(GenericService<T> service, Class<T> classModel, Class<C> classController, HttpServletRequest request) {
        this.service = service;
        this.classModel = classModel;
        this.classController = classController;
        this.request = request;
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        var object = service.findById(classModel, id);
        return new SuccessResponse<T>().handle(object, classController, request, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Busca todos")
    public ResponseEntity<List<T>> findAll() {
        var list = service.findAll();
        return new SuccessResponse<List<T>>().handle(list, classController, request, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<T>> findByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linesPerPage, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction) {
        var list = service.findByPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<T> save(@Validated @RequestBody T body) {
        var object = service.save(body);
        return new SuccessResponse<T>().handle(object, classController, request, HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<T>> saveAll(@Validated @RequestBody List<T> body) {
        var list = service.saveAll(body);
        return new SuccessResponse<List<T>>().handle(list, classController, request, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<T> update(@Validated @RequestBody T body) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method = body.getClass().getMethod(ReflectMethods.GET_ID.getDescription());
        var id = (Long) method.invoke(body);
        var object = service.update(body, id);
        return new SuccessResponse<T>().handle(object, classController, request, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<T> deleteById(@PathVariable Long id) {
        var object = service.deleteById(classModel, id);
        return new SuccessResponse<T>().handle(object, classController, request, HttpStatus.OK);
    }
}