package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends GenericControllerImpl<User, UserController> {


    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        super(userService, User.class, UserController.class, request);
    }
}
