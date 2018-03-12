package hello.service;

import hello.model.User;
import hello.model.UserDTO;
import hello.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(UserDTO userDTO) {
		User user = User.builder()
				.email(userDTO.getEmail())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.username(userDTO.getUsername())
				.build();
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;
}
