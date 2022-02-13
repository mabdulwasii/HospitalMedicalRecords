package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse {
    private String code;
    private String message;
    private Object data;
}
