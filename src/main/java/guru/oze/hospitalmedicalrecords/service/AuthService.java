package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;

public interface AuthService {
    ApiResponse authenticate(LoginDetails loginDetails) throws Exception;
    ApiResponse register(StaffInfo userInfo);
}
