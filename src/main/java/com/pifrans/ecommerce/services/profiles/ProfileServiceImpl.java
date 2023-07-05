package com.pifrans.ecommerce.services.profiles;

import com.pifrans.ecommerce.domains.entities.Profile;
import com.pifrans.ecommerce.repositories.ProfileRepository;
import com.pifrans.ecommerce.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl extends GenericServiceImpl<Profile> implements ProfileService {


    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        super(profileRepository);
    }
}
