package com.mitrais.javabootcamp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.mitrais.javabootcamp.config.Constants.RoleEnum.ADMIN;
import static com.mitrais.javabootcamp.config.Constants.RoleEnum.STUDENT;
import static java.util.stream.Collectors.toList;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
        log.info("user " + auth.getPrincipal() + " successfully login at " + new Date());
        List<String> roleList = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
        if (roleList.contains("ROLE_" + ADMIN.name())) {
            res.sendRedirect("/users/list");
        } else if (roleList.contains("ROLE_" + STUDENT.name())) {
            res.sendRedirect("/books/list");
        }
    }
}
