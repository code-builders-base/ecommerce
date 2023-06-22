package com.pifrans.ecommerce.filters;

import com.pifrans.ecommerce.errors.exceptions.ForbiddenException;
import com.pifrans.ecommerce.errors.handlers.NoControllerHandler;
import com.pifrans.ecommerce.securities.JWTSecurity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger LOG = Logger.getLogger(JWTAuthorizationFilter.class.getName());
    private final JWTSecurity jwtSecurity;
    private final UserDetailsService userDetailsService;


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTSecurity jwtSecurity, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtSecurity = jwtSecurity;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        String header = request.getHeader("Authorization");

        try {
            if (header != null && header.startsWith("Bearer ")) {
                UsernamePasswordAuthenticationToken token = getAuthentication(header.substring(7));
                if (token != null) {
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            chain.doFilter(request, response);
        } catch (ForbiddenException e) {
            NoControllerHandler.handler(request, response, e, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtSecurity.tokenValido(token)) {
            String username = jwtSecurity.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        throw new ForbiddenException("Token inválido: " + token);
    }
}