package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    ApiResponse registerStaff(StaffInfo userInfo);
    Optional<User> findByUserName(String username);
    ApiResponse updateStaff(HttpServletRequest request, StaffDto staffDto);
    ApiResponse getAllStaffs(HttpServletRequest request);

    boolean existsByUsername(String username);
}
