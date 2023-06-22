package com.pifrans.ecommerce.services.users;

import com.pifrans.ecommerce.domains.entities.User;
import com.pifrans.ecommerce.services.GenericService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends GenericService<User>, UserDetailsService {
}
