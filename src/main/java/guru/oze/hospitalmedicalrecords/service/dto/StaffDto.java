package guru.oze.hospitalmedicalrecords.service.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StaffDto implements Serializable {

    @NotNull
    private final Long id;
    @NotEmpty(message = "Name cannot be empty")
    private final String name;

    @NotEmpty(message = "Uuid cannot be empty")
    private final String uuid;
    private final LocalDate registrationDate;
}
