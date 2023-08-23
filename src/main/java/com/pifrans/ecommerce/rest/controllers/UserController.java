package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.dtos.UserDto;
import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.services.users.UserService;
import com.pifrans.ecommerce.utilities.ModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuário", description = "Endpoints para manipulação de dados di usuário APIs")
public class UserController extends GenericControllerImpl<User, UserController> {


    @Autowired
    public UserController(UserService userService, HttpServletRequest request, ModelMapperUtil modelMapperUtil) {
        super(userService, User.class, UserController.class, request, modelMapperUtil);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDto.Resume> findById(@PathVariable Long id) {
        return super.findById(UserDto.Resume.class, id);
    }

    @PostMapping
    public ResponseEntity<UserDto.Credential> save(@Validated @RequestBody User body) {
        return super.save(UserDto.Credential.class, body);
    }

    @GetMapping
    public ResponseEntity<List<UserDto.Credential>> findAll() {
        return super.findAll(UserDto.Credential.class);
    }
}
