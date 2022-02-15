package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class Jwt {
    private String accessToken;

    private String refreshToken;

    private String type = "Bearer";

    private Integer id;

    private String username;

	private List<String> roles;

	public Jwt(String accessToken, String refreshToken, Integer id, String username, List<String> roles) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}
