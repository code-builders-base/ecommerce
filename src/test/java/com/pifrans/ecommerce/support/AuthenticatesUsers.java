package com.pifrans.ecommerce.support;

import com.pifrans.ecommerce.domains.dtos.TokensDto;
import com.pifrans.ecommerce.domains.dtos.UserDto;
import com.pifrans.ecommerce.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticatesUsers {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;


    public AuthenticatesUsers(TestRestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public String createToken(String email, String password) {
        var credential = new UserDto.Credential();
        credential.setEmail(email);
        credential.setPassword(password);

        var httpEntity = new HttpEntity<>(credential);
        var response = restTemplate.exchange("/login", HttpMethod.POST, httpEntity, TokensDto.class);
        return Objects.requireNonNull(response.getBody()).getToken();
    }

    public void authenticate(String email) {
        var userDetails = userService.loadUserByUsername(email);
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    public HttpHeaders createHeader(String email, String password) {
        this.authenticate(email);

        var token = "Bearer " + this.createToken(email, password);

        var headers = new HttpHeaders();
        headers.add("Authorization", token);

        return headers;
    }
}
