package com.pifrans.ecommerce.configurations;


import com.pifrans.ecommerce.constants.PublicMatchers;
import com.pifrans.ecommerce.filters.JWTAuthenticationFilter;
import com.pifrans.ecommerce.filters.JWTValidateFilter;
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
    private final UserService userService;
    private final JWTSecurity jwtSecurity;

    public SecurityConfig(UserService userService, JWTSecurity jwtSecurity) {
        this.userService = userService;
        this.jwtSecurity = jwtSecurity;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PublicMatchers.POST.getEndpoints()).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, PublicMatchers.GET.getEndpoints()).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(PublicMatchers.ALL.getEndpoints()).permitAll().anyRequest().authenticated());

        http.addFilter(new JWTAuthenticationFilter(config.getAuthenticationManager(), jwtSecurity));
        http.addFilter(new JWTValidateFilter(config.getAuthenticationManager(), jwtSecurity, userService));
        http.addFilterAfter(new JWTAuthorizationFilter(), JWTValidateFilter.class);
        http.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        return http.build();
    }
}
