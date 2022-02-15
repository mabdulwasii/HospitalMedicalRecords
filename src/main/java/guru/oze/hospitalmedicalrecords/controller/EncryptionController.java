package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.constant.ResponseCode;
import guru.oze.hospitalmedicalrecords.utils.EncryptionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class EncryptionController {
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
}
