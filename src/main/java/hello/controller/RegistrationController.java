package hello.controller;

import hello.model.User;
import hello.model.UserDTO;
import hello.service.UserService;
import hello.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/registration")
class RegistrationController {

	@ModelAttribute("user")
	public UserDTO userRegistrationDto() {
		return new UserDTO();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
	                                  BindingResult result) {

		userValidator.validate(userDto, result);

		if (result.hasErrors()) {
			return "registration";
		}

		User existing = userService.findByUsername(userDto.getUsername());
		if (Objects.nonNull(existing)) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "registration";
		}

		userService.save(userDto);
		return "redirect:/registration?success";
	}
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;

}
