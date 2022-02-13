package guru.oze.hospitalmedicalrecords.service.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS("00", "Success"),
    FAIL("01", "Fail"),
    ERROR("01", "An error occurred!");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }



}
