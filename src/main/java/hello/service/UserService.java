package hello.service;

import hello.model.User;
import hello.payload.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User registerUser(SignUpRequest user);

}
