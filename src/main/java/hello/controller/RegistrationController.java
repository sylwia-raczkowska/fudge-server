package hello.controller;

import hello.model.User;
import hello.payload.AuthResponse;
import hello.payload.SignUpRequest;
import hello.repository.UserRepository;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
class RegistrationController {


	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		if (Objects.nonNull(userRepository.findByUsername(signUpRequest.getUsername()))) {
			return new ResponseEntity(new AuthResponse(false, "Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (Objects.nonNull(userRepository.findByEmail(signUpRequest.getEmail()))) {
			return new ResponseEntity(new AuthResponse(false, "Email sddress already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = userService.registerUser(signUpRequest);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(user.getUsername()).toUri();

		return ResponseEntity.created(location).body(new AuthResponse(true, "User registered successfully"));
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;


}
