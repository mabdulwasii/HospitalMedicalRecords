package guru.oze.hospitalmedicalrecords.service.dto;

import guru.oze.hospitalmedicalrecords.utils.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@ToString
public class StaffInfo {

    @Email(message = "Invalid email")
    private final String username;

    @ValidPassword(message = "Invalid password, please input a strong password")
    @NotEmpty(message = "Password cannot be empty")
    private final String password;

    @NotEmpty(message = "Password cannot be empty")
    private final String confirmPassword;

    @NotEmpty(message = "First name field is required")
    private String firstName;

    @NotEmpty(message = "Last name field is required")
    private String lastName;

    private LocalDate registrationDate;

    public LocalDate getRegistrationDate() {
        if (registrationDate == null) {
            return LocalDate.now();
        }
        return registrationDate;
    }
}
