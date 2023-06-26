package com.pifrans.ecommerce.filters;

import com.pifrans.ecommerce.errors.exceptions.ForbiddenException;
import com.pifrans.ecommerce.errors.handlers.NoControllerHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

public class JWTAuthorizationFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var header = request.getHeader("Authorization");

        try {
            if (response.getStatus() == HttpStatus.FORBIDDEN.value()) {
                Optional.ofNullable(header).orElseThrow(() -> new ForbiddenException("Não foi inserido um token no header 'Authorization'!"));
                Optional.of(header).filter(h -> h.startsWith("Bearer ")).orElseThrow(() -> new ForbiddenException("Token inválido: o token dever ser do tipo 'Bearer token'!"));
            }

            filterChain.doFilter(request, response);
        } catch (ForbiddenException e) {
            NoControllerHandler.handler(request, response, e, HttpStatus.FORBIDDEN);
        }
    }
}
