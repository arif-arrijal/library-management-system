package com.mitrais.javabootcamp.dao;

import com.mitrais.javabootcamp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
