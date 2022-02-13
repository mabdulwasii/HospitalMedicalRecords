package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.exception.ApiKeyNotSetException;
import guru.oze.hospitalmedicalrecords.service.StaffService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreateStaffRequest;
import guru.oze.hospitalmedicalrecords.service.dto.ResponseCode;
import guru.oze.hospitalmedicalrecords.service.dto.StaffDto;
import guru.oze.hospitalmedicalrecords.utils.EncryptionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("v1/")
public class StaffController {

    private final StaffService staffService;

    @PostMapping("staff")
    public ResponseEntity<ApiResponse> createStaff(
            @RequestBody @Valid CreateStaffRequest request
    ){
        log.info("REST request to create new staff {}", request);
        ApiResponse response = staffService.createStaff(request);
        log.info("created staff api response {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("staff")
    public ResponseEntity<ApiResponse> updateStaff(
            @RequestBody @Valid StaffDto staffDto,
            @RequestHeader("x-api-key") String apiKey
    ) {
        log.debug("REST request to update staff : {}", staffDto);
        if (apiKey == null) throw new ApiKeyNotSetException("Access denied, headers not set");
        ApiResponse response = staffService.updateStaff(staffDto, apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/encrypt/{value}")
    public ResponseEntity<ApiResponse> getEncyptedValue(@PathVariable String value){
        String encrypt = EncryptionUtil.encrypt(value);
        ApiResponse response = ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(encrypt)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/staff")
    public ResponseEntity<ApiResponse> getAllStaffs(@RequestHeader("x-api-key") String apikey){
        ApiResponse response = staffService.getAllStaffs(apikey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
