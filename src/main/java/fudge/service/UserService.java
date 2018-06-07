package fudge.service;

import fudge.model.User;
import fudge.payload.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User registerUser(SignUpRequest user);

    Long findIdByEmail(String email);

}
