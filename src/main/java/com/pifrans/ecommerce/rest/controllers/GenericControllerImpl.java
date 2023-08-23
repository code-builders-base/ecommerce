package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.constants.ReflectMethods;
import com.pifrans.ecommerce.rest.responses.SuccessResponse;
import com.pifrans.ecommerce.services.GenericService;
import com.pifrans.ecommerce.utilities.ModelMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class GenericControllerImpl<E, C> implements GenericController<E> {
    private final GenericService<E> service;
    private final Class<E> classModel;
    private final Class<C> classController;
    private final HttpServletRequest request;
    private final ModelMapperUtil modelMapperUtil;


    protected GenericControllerImpl(GenericService<E> service, Class<E> classModel, Class<C> classController, HttpServletRequest request, ModelMapperUtil modelMapperUtil) {
        this.service = service;
        this.classModel = classModel;
        this.classController = classController;
        this.request = request;
        this.modelMapperUtil = modelMapperUtil;
    }

    public <D> ResponseEntity<D> findById(Class<D> classDto, Long id) {
        var entity = service.findById(classModel, id);
        var dto = modelMapperUtil.toDto(entity, classDto);
        return new SuccessResponse<D>().handle(dto, classController, request, HttpStatus.OK);
    }

    public <D> ResponseEntity<List<D>> findAll(Class<D> classDto) {
        var entities = service.findAll();
        var dtos = entities.stream().map(e -> modelMapperUtil.toDto(e, classDto)).toList();
        return new SuccessResponse<List<D>>().handle(dtos, classController, request, HttpStatus.OK);
    }

    public <D> ResponseEntity<Page<D>> findByPage(Class<D> classDto, Integer page, Integer linesPerPage, String orderBy, String direction) {
        var entityPages = service.findByPage(page, linesPerPage, orderBy, direction);
        var entities = entityPages.getContent();
        var dtos = entities.stream().map(e -> modelMapperUtil.toDto(e, classDto)).toList();
        var dtoPages = new PageImpl<>(dtos, entityPages.getPageable(), entityPages.getTotalElements());
        return ResponseEntity.ok().body(dtoPages);
    }

    public <D> ResponseEntity<D> save(Class<D> classDto, E body) {
        var entity = service.save(body);
        var dto = modelMapperUtil.toDto(entity, classDto);
        return new SuccessResponse<D>().handle(dto, classController, request, HttpStatus.CREATED);
    }

    public <D> ResponseEntity<List<D>> saveAll(Class<D> classDto, List<E> body) {
        var entities = service.saveAll(body);
        var dtos = entities.stream().map(e -> modelMapperUtil.toDto(e, classDto)).toList();
        return new SuccessResponse<List<D>>().handle(dtos, classController, request, HttpStatus.CREATED);
    }

    public <D> ResponseEntity<D> update(Class<D> classDto, E body) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method = body.getClass().getMethod(ReflectMethods.GET_ID.getDescription());
        var id = (Long) method.invoke(body);
        var entity = service.update(body, id);
        var dto = modelMapperUtil.toDto(entity, classDto);
        return new SuccessResponse<D>().handle(dto, classController, request, HttpStatus.OK);
    }

    public <D> ResponseEntity<D> deleteById(Class<D> classDto, Long id) {
        var entity = service.deleteById(classModel, id);
        var dto = modelMapperUtil.toDto(entity, classDto);
        return new SuccessResponse<D>().handle(dto, classController, request, HttpStatus.OK);
    }
}