package com.pifrans.ecommerce.configurations;


import com.pifrans.ecommerce.filters.JWTAuthenticationFilter;
import com.pifrans.ecommerce.filters.JWTAuthorizationFilter;
import com.pifrans.ecommerce.securities.JWTSecurity;
import com.pifrans.ecommerce.services.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
    private static final String[] PUBLIC_MATCHERS_GET = {/*"/users/**"*/ "/products/**"};
    private static final String[] PUBLIC_MATCHERS_POST = {"/auth/forgot/**"};


    private final UserService userService;
    private final JWTSecurity jwtSecurity;

    public SecurityConfig(UserService userService, JWTSecurity jwtSecurity) {
        this.userService = userService;
        this.jwtSecurity = jwtSecurity;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated());

        http.addFilter(new JWTAuthenticationFilter(config.getAuthenticationManager(), jwtSecurity));
        http.addFilter(new JWTAuthorizationFilter(config.getAuthenticationManager(), jwtSecurity, userService));
        http.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        return http.build();
    }
}
