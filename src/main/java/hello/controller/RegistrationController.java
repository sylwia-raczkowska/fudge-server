package hello.controller;

import hello.model.User;
import hello.payload.AuthResponse;
import hello.payload.AuthenticationResponse;
import hello.payload.LoginRequest;
import hello.payload.SignUpRequest;
import hello.repository.UserRepository;
import hello.security.JwtTokenProvider;
import hello.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@Slf4j
class RegistrationController {
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		log.info("Signup Request: {}", signUpRequest);
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new AuthResponse(false, "Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new AuthResponse(false, "Email address already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = userService.registerUser(signUpRequest);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(user.getUsername()).toUri();

		return ResponseEntity.created(location).body(new AuthResponse(true, "User registered successfully"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}
