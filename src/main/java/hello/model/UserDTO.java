package hello.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserDTO {
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	private String confirmPassword;

	@Email
	@NotEmpty
	private String email;
}
