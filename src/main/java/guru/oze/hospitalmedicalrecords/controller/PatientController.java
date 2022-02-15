package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.entity.Patient;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
@Slf4j
public class PatientController {
    private final PatientService service;

    @PostMapping("patient")
    public ResponseEntity<ApiResponse> createPatient(
            @RequestBody CreatePatientRequest request
    ){
        log.info("REST request to create patient {}", request);
        ApiResponse response = service.createPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("patient")
    public ResponseEntity<ApiResponse> updatePatientProfile(
            @RequestBody Patient patient
    ){
        log.info("REST request to update patient {}", patient);
        ApiResponse response = service.updatePatientProfile(patient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("patient/upToTwo")
    public ResponseEntity<ApiResponse> fetchPatientsWithAgeUpToTwoYears(){
        log.info("REST request to fetch patients with age up to two years");
        ApiResponse response = service.fetchPatientsWithAgeUpToTwoYears();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "patient/export/csv/{patientId}")
    public void exportPatientProfileToCsv(
            HttpServletResponse response,
            @PathVariable Integer patientId
    ) {
        log.info("REST request to export a patient profile to csv {}", patientId);
        service.exportPatientProfileToCsv(patientId, response);
    }

    @PostMapping("patient/{startDate}/{endDate}")
    public ResponseEntity<ApiResponse> deletePatientByDateRange(
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) {
        ApiResponse response = service.deletePatientByDateRange(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
