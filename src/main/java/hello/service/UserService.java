package hello.service;

import hello.model.User;
import hello.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User findByUsername(String username);

	User save(UserDTO user);

}
