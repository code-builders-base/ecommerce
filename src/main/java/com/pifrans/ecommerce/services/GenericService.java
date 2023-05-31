package com.pifrans.ecommerce.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericService<T> {

    T findById(Class<T> tClass, Long id);

    T save(T object) throws DataIntegrityViolationException;

    List<T> saveAll(List<T> list) throws DataIntegrityViolationException;

    T update(T object, Long id) throws DataIntegrityViolationException;

    T deleteById(Class<T> tClass, Long id);

    List<T> findAll();

    Page<T> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction);
}
