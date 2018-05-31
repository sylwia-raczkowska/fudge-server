package hello.service;

import hello.model.User;
import hello.payload.SignUpRequest;
import hello.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
  
	@Override
	public User registerUser(SignUpRequest request) {
		log.info("registerUser : {}", request);
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.username(request.getUsername())
				.build();
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}
}
