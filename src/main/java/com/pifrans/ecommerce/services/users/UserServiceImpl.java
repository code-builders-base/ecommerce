package com.pifrans.ecommerce.services.users;

import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.repositories.UserRepository;
import com.pifrans.ecommerce.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }
}
