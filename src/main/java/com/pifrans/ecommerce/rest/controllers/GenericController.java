package com.pifrans.ecommerce.rest.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface GenericController<T> {

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<T>> findAll();

    @GetMapping("/page")
    ResponseEntity<Page<T>> findByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linesPerPage, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction);

    @PostMapping
    ResponseEntity<T> save(@Validated @RequestBody T body);

    @PostMapping("/saveAll")
    ResponseEntity<List<T>> saveAll(@Validated @RequestBody List<T> body);

    @PutMapping
    ResponseEntity<T> update(@Validated @RequestBody T body) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    @DeleteMapping("/{id}")
    ResponseEntity<T> deleteById(@PathVariable Long id);
}
