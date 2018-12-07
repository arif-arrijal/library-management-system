package com.mitrais.javabootcamp.service.impl;

import com.mitrais.javabootcamp.dao.BookshelfDao;
import com.mitrais.javabootcamp.model.Bookshelf;
import com.mitrais.javabootcamp.service.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class BookshelfServiceImpl implements BookshelfService {
    @Autowired
    private BookshelfDao bookshelfDao;

    @Override
    @Transactional(readOnly = true)
    public List<Bookshelf> findAll() {
        return bookshelfDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Bookshelf findOne(Long id) {
        return bookshelfDao.findById(id).orElseThrow(() -> new RuntimeException("Bookshelf with id " + id + " is not exist"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Bookshelf data) {
        Bookshelf savedData;
        if (data.getId() == null) {
            savedData = data;
        } else {
            savedData = bookshelfDao.findById(data.getId()).orElse(new Bookshelf());
            copyProperties(data, savedData);
        }
        bookshelfDao.save(savedData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        bookshelfDao.deleteById(id);
    }
}
