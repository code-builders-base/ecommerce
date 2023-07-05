package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.entities.Profile;
import com.pifrans.ecommerce.services.profiles.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileController extends GenericControllerImpl<Profile, ProfileController> {


    @Autowired
    public ProfileController(ProfileService profileService, HttpServletRequest request) {
        super(profileService, Profile.class, ProfileController.class, request);
    }
}
