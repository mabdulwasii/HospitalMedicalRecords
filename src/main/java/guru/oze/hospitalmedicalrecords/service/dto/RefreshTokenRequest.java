package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {
	@NotBlank(message = "Refresh token is required")
	private String refreshToken;
}
