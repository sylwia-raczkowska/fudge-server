package hello.config;

import hello.repository.UserRepository;
import hello.service.UserService;
import hello.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class UserInjector {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}

	@Autowired
	UserService userService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		return new UserServiceImpl(userRepository, passwordEncoder);
	}
}
