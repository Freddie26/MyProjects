package com.example.rest.service;

import java.util.List;

interface CustomEntityService<T> {
    List<T> findAll();
    T findById(Long id);
    boolean update(T entity, Long id);
    boolean delete(Long id);
}
