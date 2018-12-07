package com.mitrais.javabootcamp.dao;

import com.mitrais.javabootcamp.model.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfDao extends JpaRepository<Bookshelf, Long> {
}
