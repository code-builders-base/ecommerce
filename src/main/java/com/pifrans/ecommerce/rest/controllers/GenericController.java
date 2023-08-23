package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.errors.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface GenericController<E> {

    <D> ResponseEntity<D> findById(Class<D> classDto, Long id) throws NotFoundException;

    <D> ResponseEntity<List<D>> findAll(Class<D> classDto);

    <D> ResponseEntity<Page<D>> findByPage(Class<D> classDto, Integer page, Integer linesPerPage, String orderBy, String direction);

    <D> ResponseEntity<D> save(Class<D> classDto, E body);

    <D> ResponseEntity<List<D>> saveAll(Class<D> classDto, List<E> body);

    <D> ResponseEntity<D> update(Class<D> classDto, E body) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    <D> ResponseEntity<D> deleteById(Class<D> classDto, Long id);
}
