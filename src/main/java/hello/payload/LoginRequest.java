package hello.payload;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class LoginRequest {

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
