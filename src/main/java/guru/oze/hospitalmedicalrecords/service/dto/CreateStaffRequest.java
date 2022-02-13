package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class CreateStaffRequest {
    @NotEmpty(message = "Name field is required")
    private String name;

    private LocalDate registrationDate;

    public LocalDate getRegistrationDate() {
        if (registrationDate == null){
            return LocalDate.now();
        }
        return registrationDate;
    }
}
