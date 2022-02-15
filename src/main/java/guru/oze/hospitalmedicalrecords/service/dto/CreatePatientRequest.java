package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreatePatientRequest {
    @NotEmpty(message = "Patient first name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Patient last name cannot be empty")
    private String lastName;
    @NotNull(message = "Age is required")
    private Integer age;
    private LocalDateTime lastVisitDate;

    public LocalDateTime getLastVisitDate() {
        if (lastVisitDate == null) {
            return LocalDateTime.now();
        }
        return lastVisitDate;
    }
}
