package com.mitrais.javabootcamp.dao;

import com.mitrais.javabootcamp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {
}
