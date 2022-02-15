package guru.oze.hospitalmedicalrecords.service.dto;

import guru.oze.hospitalmedicalrecords.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class CreateStaffRequest {
    @NotEmpty(message = "First name field is required")
    private String firstName;

    @NotEmpty(message = "Last name field is required")
    private String lastName;

    private LocalDate registrationDate;

    private User user;

    public LocalDate getRegistrationDate() {
        if (registrationDate == null){
            return LocalDate.now();
        }
        return registrationDate;
    }
}
