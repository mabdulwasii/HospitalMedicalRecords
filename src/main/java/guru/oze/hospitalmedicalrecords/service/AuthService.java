package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenRequest;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;

public interface AuthService {
    ApiResponse authenticate(LoginDetails loginDetails) throws Exception;
    ApiResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    ApiResponse register(StaffInfo userInfo);
}
