package com.pifrans.ecommerce.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pifrans.ecommerce.domains.dtos.TokensDto;
import com.pifrans.ecommerce.domains.dtos.UserDto;
import com.pifrans.ecommerce.errors.handlers.NoControllerHandler;
import com.pifrans.ecommerce.securities.JWTSecurity;
import com.pifrans.ecommerce.securities.UserDetailSecurity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOG = Logger.getLogger(JWTAuthenticationFilter.class.getName());
    private final AuthenticationManager authenticationManager;
    private final JWTSecurity jwtSecurity;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTSecurity jwtSecurity) {
        this.authenticationManager = authenticationManager;
        this.jwtSecurity = jwtSecurity;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var credenciaisDTO = new ObjectMapper().readValue(request.getInputStream(), UserDto.Credential.class);
            var authenticationToken = new UsernamePasswordAuthenticationToken(credenciaisDTO.getEmail(), credenciaisDTO.getPassword(), new ArrayList<>());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            LOG.severe(e.getMessage());
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        var username = ((UserDetailSecurity) authResult.getPrincipal()).getUsername();
        var token = jwtSecurity.generateToken(username);

        var tokensDto = new TokensDto("Bearer", token);
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(tokensDto);

        response.setContentType("application/json");
        response.getWriter().append(json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        NoControllerHandler.handler(request, response, failed, HttpStatus.UNAUTHORIZED);
    }
}