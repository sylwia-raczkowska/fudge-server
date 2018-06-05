package hello.payload;

import lombok.Data;

@Data
public class TokenResponse {

	private String accessToken;
	private String tokenType = "Bearer";
}
