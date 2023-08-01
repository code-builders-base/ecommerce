package com.pifrans.ecommerce.rest.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pifrans.ecommerce.EcommerceApplicationTestsContext;
import com.pifrans.ecommerce.domains.dtos.CommonErroDto;
import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.support.AuthenticatesUsers;
import com.pifrans.ecommerce.support.pages.RestResponsePage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTests extends EcommerceApplicationTestsContext {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticatesUsers authenticatesUsers;

    private HttpHeaders headers;


    @BeforeEach
    void setup() {
        headers = authenticatesUsers.createHeader("master@email.com", "Ps123456");
    }

    @Test
    @Order(1)
    @DisplayName("Testa o método findAll da classe UserController")
    void testSuccessForFindAll() {
        var response = restTemplate.exchange("/users", HttpMethod.GET, new HttpEntity<>(headers), User[].class);
        var body = List.of(Objects.requireNonNull(response.getBody()));

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Espera que o retornop seja 200");
        assertEquals(1, body.size(), "Espera que tenha um usuário na lista");
    }

    @Test
    @Order(2)
    void testSuccessForFindById() {
        var response = restTemplate.exchange("/users/1", HttpMethod.GET, new HttpEntity<>(headers), User.class);
        var body = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, body.getId());
    }

    @Test
    @Order(3)
    void testFailForFindById() {
        var response = restTemplate.exchange("/users/100", HttpMethod.GET, new HttpEntity<>(headers), CommonErroDto[].class);
        var body = List.of(Objects.requireNonNull(response.getBody()));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(1, body.size());
    }

    @Test
    @Order(4)
    void testSuccessForFindByPage() throws JsonProcessingException {
        var response = restTemplate.exchange("/users/page", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        var pageUsers = new ObjectMapper().readValue(response.getBody(), new TypeReference<RestResponsePage<User>>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(24, pageUsers.getPageable().getPageSize());
        assertEquals(1, pageUsers.getContent().size());
    }

    @Test
    @Order(5)
    void testSuccessForSave() {
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("Top");
        user.setPassword("Ps123456");
        user.setEmail("test@email.com");

        var httpEntity = new HttpEntity<>(user, headers);
        var response = restTemplate.exchange("/users", HttpMethod.POST, httpEntity, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
