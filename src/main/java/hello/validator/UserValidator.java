package hello.validator;

import hello.model.UserDTO;
import hello.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {

	private static final int MIN_LENGTH = 6;
	private static final int MAX_LENGTH = 32;

	@Override
	public boolean supports(Class<?> aClass) {
		return UserDTO.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserDTO user = (UserDTO) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (user.getUsername().length() < MIN_LENGTH || user.getUsername().length() > MAX_LENGTH) {
			errors.rejectValue("username", "");
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < MIN_LENGTH || user.getPassword().length() > MAX_LENGTH) {
			errors.rejectValue("password", "");
		}

		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "");
		}
	}

	private UserService userService;
}