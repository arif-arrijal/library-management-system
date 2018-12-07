package com.mitrais.javabootcamp.service;

import com.mitrais.javabootcamp.model.Role;
import com.mitrais.javabootcamp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Role> findAllRole();
    User registerParticipant(User user);
    List<User> findAll(String username);
    User findOne(Long id);
    void saveOrUpdate(User user);
    void delete(Long id);
}
