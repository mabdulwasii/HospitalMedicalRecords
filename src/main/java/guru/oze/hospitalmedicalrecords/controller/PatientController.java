package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.service.PatientService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("v1/")
@Slf4j
public class PatientController {
    private final PatientService service;

    @PostMapping("patient")
    public ResponseEntity<ApiResponse> createPatient(
            @RequestBody CreatePatientRequest request,
            @RequestHeader("x-api-key") String apiKey
    ){
        log.info("REST request to create patient {}", request);
        ApiResponse response = service.createPatient(request, apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("patient/upToTwo")
    public ResponseEntity<ApiResponse> fetchPatientsWithAgeUpToTwoYears(@RequestHeader("x-api-key") String apiKey){
        log.info("REST request to fetch patients with age up to two years {}", apiKey);
        ApiResponse response = service.fetchPatientsWithAgeUpToTwoYears(apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "patient/export/csv/{patientId}")
    public void exportPatientProfileToCsv(
            HttpServletResponse response,
            @PathVariable Long patientId,
            @RequestHeader("x-api-key") String apiKey
    ) {
        log.info("REST request to export a patient profile to csv {}", patientId);
        service.exportPatientProfileToCsv(patientId, apiKey, response);
    }
}
