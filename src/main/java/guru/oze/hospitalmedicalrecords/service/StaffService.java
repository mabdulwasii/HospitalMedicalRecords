package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.Staff;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreateStaffRequest;

public interface StaffService {
    ApiResponse createStaff(CreateStaffRequest request);
    ApiResponse updateStaff(Staff staff, String apiKey);
    ApiResponse getAllStaffs(String apikey);
}
