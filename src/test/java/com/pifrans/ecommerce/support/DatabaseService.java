package com.pifrans.ecommerce.support;

import com.pifrans.ecommerce.domains.entities.Profile;
import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.services.profiles.ProfileService;
import com.pifrans.ecommerce.services.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DatabaseService {
    private final ProfileService profileService;
    private final UserService userService;


    public DatabaseService(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    public void create() {
        var profileAdmin = new Profile();
        profileAdmin.setName("ROLE_ADMIN");

        var profileUser = new Profile();
        profileUser.setName("ROLE_USER");

        var profiles = profileService.saveAll(List.of(profileAdmin, profileUser));

        var user = new User();
        user.setFirstName("Master");
        user.setLastName("Top");
        user.setPassword("Ps123456");
        user.setEmail("master@email.com");
        user.setProfiles(Set.copyOf(profiles));
        userService.save(user);
    }
}
