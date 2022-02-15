package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDetails {
    @NotEmpty(message = "Username cannot be empty")
    private final String username;

    @NotEmpty(message = "Password cannot be empty")
    private final String password;
}
