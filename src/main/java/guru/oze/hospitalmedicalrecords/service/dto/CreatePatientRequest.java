package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreatePatientRequest {
    @NotEmpty(message = "Patient name cannot be empty")
    private String name;
    @NotNull(message = "Age is required")
    private Integer age;
    private LocalDate lastVisitDate;

    public LocalDate getLastVisitDate() {
        if (lastVisitDate == null) {
            return LocalDate.now();
        }
        return lastVisitDate;
    }
}
