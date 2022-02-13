package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreateStaffRequest;
import guru.oze.hospitalmedicalrecords.service.dto.StaffDto;

public interface StaffService {
    ApiResponse createStaff(CreateStaffRequest request);
    ApiResponse updateStaff(StaffDto staffDto, String apiKey);
    ApiResponse getAllStaffs(String apikey);
}
