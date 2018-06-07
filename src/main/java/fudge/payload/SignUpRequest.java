package fudge.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min=6, max = 32)
	private String username;

	@NotBlank
	@Size(min=6, max = 32)
	private String password;

}
