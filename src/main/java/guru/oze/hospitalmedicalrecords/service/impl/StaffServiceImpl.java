package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.Staff;
import guru.oze.hospitalmedicalrecords.exception.FailedDecryptionException;
import guru.oze.hospitalmedicalrecords.exception.InvalidAccessKeyException;
import guru.oze.hospitalmedicalrecords.repository.StaffRepository;
import guru.oze.hospitalmedicalrecords.service.StaffService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreateStaffRequest;
import guru.oze.hospitalmedicalrecords.service.dto.StaffDto;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import guru.oze.hospitalmedicalrecords.utils.EncryptionUtil;
import guru.oze.hospitalmedicalrecords.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
    private final StaffRepository repository;
    private final SecurityUtil securityUtil;

    public ApiResponse createStaff(CreateStaffRequest request) {
        Staff staff = Staff.builder()
                .name(request.getName())
                .registrationDate(request.getRegistrationDate())
                .build();
        Staff createdStaff = repository.save(staff);
        log.info("Created staff {}", createdStaff);
        return DtoTransformer.buildApiResponse("Staff created successfully", createdStaff);
    }

    @Override
    public ApiResponse updateStaff(StaffDto staffDto, String apiKey) {
        securityUtil.ensureApiKeyIsValid(apiKey);
        Staff staff = Staff.builder()
                .id(staffDto.getId())
                .name(staffDto.getName())
                .uuid(staffDto.getUuid())
                .registrationDate(staffDto.getRegistrationDate())
                .build();

        Staff updatedStaff = repository.save(staff);
        log.info("Updated staff {}", updatedStaff);
        return DtoTransformer.buildApiResponse("Staff updated successfully", updatedStaff);
    }

    @Override
    public ApiResponse getAllStaffs(String apikey) {
        securityUtil.ensureApiKeyIsValid(apikey);
        List<Staff> staffList = repository.findAll();
        log.info("Retrieved staff list {}", staffList);
        return DtoTransformer.buildApiResponse("Staff updated successfully", staffList);
    }
}
