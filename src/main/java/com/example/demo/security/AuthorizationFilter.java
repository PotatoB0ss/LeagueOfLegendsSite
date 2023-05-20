package com.example.demo.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class AuthorizationFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        // Проверка авторизации пользователя
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if(request.getRequestURL().toString().equals("http://localhost:8080/logout")){

                HttpSession session = request.getSession(false); // Получение текущей сессии
                if (session != null) {
                    session.invalidate(); // Инвалидация (очистка) сессии
                }

                SecurityContextHolder.getContext().setAuthentication(null);
                response.sendRedirect("/main");
                return;
            }


        }

        filterChain.doFilter(request, response);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

    }



}