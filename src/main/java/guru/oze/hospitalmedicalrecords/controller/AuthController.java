package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenRequest;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping({"/register"})
    public ResponseEntity<?> register(@Valid @RequestBody StaffInfo staffInfo) {
        if (userService.existsByUsername(staffInfo.getUsername())) {
            throw new GenericException("Error: Username taken. Please input another username");
        }
        var register = authService.register(staffInfo);
        return ResponseEntity.ok().body(register);
    }

    @PostMapping({"/authenticate"})
    public ResponseEntity<ApiResponse> authenticate(@Valid @RequestBody LoginDetails loginDetails) throws Exception {
        ApiResponse response = authService.authenticate(loginDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping({"/refresh_token"})
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        ApiResponse response = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("staff/update")
    public ResponseEntity<ApiResponse> updateStaff(@RequestBody @Valid StaffDto staffDto) {
        log.debug("REST request to update staff : {}", staffDto);
        ApiResponse response = userService.updateStaff(staffDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("staff")
    public ResponseEntity<ApiResponse> getAllStaffs(){
        ApiResponse response = userService.getAllStaffs();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
