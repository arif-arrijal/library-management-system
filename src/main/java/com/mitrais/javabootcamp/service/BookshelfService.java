package com.mitrais.javabootcamp.service;

import com.mitrais.javabootcamp.model.Bookshelf;

import java.util.List;

public interface BookshelfService {
    List<Bookshelf> findAll();
    Bookshelf findOne(Long id);
    void saveOrUpdate(Bookshelf bookshelf);
    void delete(Long id);
}
