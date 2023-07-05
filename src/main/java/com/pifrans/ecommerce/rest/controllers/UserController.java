package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends GenericControllerImpl<User, UserController> {


    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        super(userService, User.class, UserController.class, request);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return super.findById(id);
    }
}
