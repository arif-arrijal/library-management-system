package com.mitrais.javabootcamp.service;

import com.mitrais.javabootcamp.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findOne(Long id);
    void saveOrUpdate(Book book);
    void delete(Long id);
}
