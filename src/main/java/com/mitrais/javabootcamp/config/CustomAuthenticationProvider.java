package com.mitrais.javabootcamp.config;

import com.mitrais.javabootcamp.dao.UserDao;
import com.mitrais.javabootcamp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.mitrais.javabootcamp.config.AppUtil.shaPassword;
import static java.util.stream.Collectors.toList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDao userDao;

    @Autowired
    public CustomAuthenticationProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        Optional<User> oLoginUser = userDao.findByUsername(username);
        User loginUser = oLoginUser.orElseThrow(() -> new BadCredentialsException("User is not found"));
        if (!loginUser.getPassword().equals(shaPassword(password))) throw new BadCredentialsException("Password is not match");

        List<SimpleGrantedAuthority> roleList = loginUser.getRoleList()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(username, password, roleList);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
