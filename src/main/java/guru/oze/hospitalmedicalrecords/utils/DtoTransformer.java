package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.entity.Staff;
import guru.oze.hospitalmedicalrecords.service.constant.ResponseCode;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import guru.oze.hospitalmedicalrecords.service.dto.CreateStaffRequest;

public class DtoTransformer {

    public static ApiResponse buildApiResponse(Object data) {
        return buildApiResponse(ResponseCode.SUCCESS.getMessage(), data);
    }

    public static ApiResponse buildApiResponse(String message, Object data) {
        return ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static Patient transformCreatePatientRequestToPatientEntity(CreatePatientRequest request) {
        return Patient.builder()
                .name(request.getName())
                .age(request.getAge())
                .lastVisitDate(request.getLastVisitDate())
                .build();
    }

    public static Staff transformCreateStaffRequestToStaffEntity(CreateStaffRequest request) {
        return Staff.builder()
                .name(request.getName())
                .registrationDate(request.getRegistrationDate())
                .build();
    }
}
