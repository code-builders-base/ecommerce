package com.pifrans.ecommerce.services.users;

import com.pifrans.ecommerce.constants.SecurityProfiles;
import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.repositories.UserRepository;
import com.pifrans.ecommerce.securities.UserDetailSecurity;
import com.pifrans.ecommerce.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User save(User object) throws DataIntegrityViolationException {
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.save(object);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(email);
            }
            return new UserDetailSecurity(user.getId(), user.getEmail(), user.getPassword(), Set.of(SecurityProfiles.ADMIN));
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(email);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
