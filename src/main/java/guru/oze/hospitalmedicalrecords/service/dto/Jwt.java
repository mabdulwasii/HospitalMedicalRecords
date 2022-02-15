package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class Jwt {
    private String accessToken;

    private String type = "Bearer";

    private Integer id;

    private String username;

	private List<String> roles;

	public Jwt(String accessToken, Integer id, String username, List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
}
