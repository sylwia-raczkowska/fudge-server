package fudge.payload;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationResponse {

	@NonNull
	private String accessToken;
	private String tokenType = "Bearer";

}
