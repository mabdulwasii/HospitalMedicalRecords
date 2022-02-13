package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.service.PatientService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/")
@Slf4j
public class PatientController {

    private final PatientService service;

    @PostMapping("/patient")
    public ResponseEntity<ApiResponse> createPatient(
            @RequestBody CreatePatientRequest request,
            @RequestHeader("x-api-key") String apiKey
    ){
        log.info("Request to create patient {}", request);
        ApiResponse response = service.createPatient(request, apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/patient/upToTwo")
    public ResponseEntity<ApiResponse> fetchPatientsAgeUpToTwoYears(@RequestHeader("x-api-key") String apiKey){
        ApiResponse response = service.fetchPatientsAgeUpToTwoYears(apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
