package com.mitrais.javabootcamp.service.impl;

import com.mitrais.javabootcamp.dao.RoleDao;
import com.mitrais.javabootcamp.dao.UserDao;
import com.mitrais.javabootcamp.model.Role;
import com.mitrais.javabootcamp.model.User;
import com.mitrais.javabootcamp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.mitrais.javabootcamp.config.AppUtil.shaPassword;
import static com.mitrais.javabootcamp.config.Constants.RoleEnum.STUDENT;
import static java.util.Collections.singletonList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllRole() {
        return roleDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerParticipant(User user) {
        Role participantRole = roleDao.findByName(STUDENT.name());
        user.setPassword(shaPassword(user.getPassword()));
        user.setRoleList(singletonList(participantRole));
        return userDao.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(User data) {
        User savedData;
        List<Role> roleList = roleDao.findAllById(data.getRoleIdList());
        if (data.getId() == null) {
            savedData = data;
            savedData.setPassword(shaPassword(data.getPassword()));
        } else {
            savedData = userDao.findById(data.getId()).orElse(new User());
            copyProperties(data, savedData, "password");
            if (!data.getPassword().isEmpty()) savedData.setPassword(shaPassword(data.getPassword()));
        }
        savedData.setRoleList(roleList);
        userDao.save(savedData);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(String username) {
        List<User> userList = userDao.findAll();
        userList.removeIf(user -> user.getUsername().equals(username));
        return userList;
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        User user = userDao.findById(id).orElse(new User());
        if (!user.getRoleList().isEmpty()) {
            user.setRoleIdList(user.getRoleList().stream()
                    .map(Role::getId)
                    .collect(Collectors.toList()));
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userDao.deleteById(id);
    }


}
