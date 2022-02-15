package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.entity.Authority;
import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.service.constant.ResponseCode;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DtoTransformer {

    public static ApiResponse buildApiResponse(Object data) {
        return buildApiResponse(ResponseCode.SUCCESS.getMessage(), data);
    }

    public static ApiResponse buildApiResponse(String message, Object data) {
        return ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(message)               .data(data)
                .build();
    }

    public static Patient transformCreatePatientRequestToPatientEntity(CreatePatientRequest request) {
        return Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .lastVisitDate(request.getLastVisitDate())
                .build();
    }

    public static StaffDto transformUserEntityToStaffDto(User user) {
        return StaffDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .uuid(user.getUuid())
                .registrationDate(user.getRegistrationDate())
                .build();
    }

    public static User transformStaffDtoToUserEntity(StaffDto staffDto) {
        return User.builder()
                .firstName(staffDto.getFirstName())
                .lastName(staffDto.getLastName())
                .registrationDate(staffDto.getRegistrationDate())
                .uuid(staffDto.getUuid())
                .id(staffDto.getId())
                .username(staffDto.getUsername())
                .build();
    }

    public static User transformStaffInfoToUserEntity(StaffInfo staffInfo, String encryptedPassword, Set<Authority> authorities) {
        return User.builder()
                .password(encryptedPassword)
                .activated(true)
                .authorities(authorities)
                .username(staffInfo.getUsername())
                .firstName(staffInfo.getFirstName())
                .lastName(staffInfo.getLastName())
                .registrationDate(staffInfo.getRegistrationDate())
                .uuid(String.valueOf(UUID.randomUUID()))
                .build();
    }

    public static List<StaffDto> transformUserEntityListToStaffDtoList(List<User> staffList) {
        ArrayList<StaffDto> staffDtos = new ArrayList<>();
        for (User user : staffList) {
            staffDtos.add(transformUserEntityToStaffDto(user));
        }
        return staffDtos;
    }
}
