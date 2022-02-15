package guru.oze.hospitalmedicalrecords.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class StaffDto implements Serializable {
    private final Integer id;
    @NotNull
    @Size(min = 5, max = 200)
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String uuid;
    private final LocalDate registrationDate;
}
