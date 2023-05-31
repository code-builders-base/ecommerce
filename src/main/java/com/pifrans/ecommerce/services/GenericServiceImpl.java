package com.pifrans.ecommerce.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
    private final JpaRepository<T, Long> repository;


    protected GenericServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T findById(Class<T> tClass, Long id) {
        String message = String.format("Nenhum item (%s) de ID (%d) encontrado!", tClass.getSimpleName(), id);
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
    }

    public T save(T object) throws DataIntegrityViolationException {
        return repository.save(object);
    }

    public List<T> saveAll(List<T> list) throws DataIntegrityViolationException {
        return repository.saveAll(list);
    }

    public T update(T object, Long id) throws DataIntegrityViolationException {
        if (repository.existsById(id)) {
            return repository.save(object);
        }
        String message = String.format("Não foi possível atualizar (%s) de ID (%d), não encontrado!", object.getClass().getSimpleName(), id);
        throw new NoSuchElementException(message);
    }

    public T deleteById(Class<T> tClass, Long id) {
        String message = String.format("Não foi possível excluir o item, pois não existe (%s) com ID (%d)!", tClass.getSimpleName(), id);
        T object = repository.findById(id).orElseThrow(() -> new NoSuchElementException(message));

        repository.deleteById(id);
        return object;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}