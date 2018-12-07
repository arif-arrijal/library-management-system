package com.mitrais.javabootcamp.service.impl;

import com.mitrais.javabootcamp.dao.BookDao;
import com.mitrais.javabootcamp.model.Book;
import com.mitrais.javabootcamp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findOne(Long id) {
        return bookDao.findById(id).orElseThrow(() -> new RuntimeException("Book with id " + id + " is not exist"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Book data) {
        Book savedData;
        if (data.getId() == null) {
            savedData = data;
        } else {
            savedData = bookDao.findById(data.getId()).orElse(new Book());
            copyProperties(data, savedData);
        }
        bookDao.save(savedData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        bookDao.deleteById(id);
    }
}
