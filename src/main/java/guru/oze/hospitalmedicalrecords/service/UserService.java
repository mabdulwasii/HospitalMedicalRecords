package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;

import java.util.Optional;

public interface UserService {
    ApiResponse registerStaff(StaffInfo userInfo);
    Optional<User> findByUserName(String username);
    ApiResponse updateStaff(StaffDto staffDto);
    ApiResponse getAllStaffs();

    boolean existsByUsername(String username);
}
