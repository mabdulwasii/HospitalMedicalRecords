package guru.oze.hospitalmedicalrecords.controller;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.exception.FailedExportToCsvException;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
@Slf4j
public class PatientController {
    private final PatientService service;

    @PostMapping("/patient")
    public ResponseEntity<ApiResponse> createPatient(
            @RequestBody CreatePatientRequest createPatientRequest,
            HttpServletRequest request
    ){
        log.info("REST request to create patient {}", request);
        ApiResponse response = service.createPatient(request, createPatientRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/patient")
    public ResponseEntity<ApiResponse> updatePatientProfile(
            @RequestBody Patient patient,
            HttpServletRequest request
    ){
        log.info("REST request to update patient {}", patient);
        if (patient.getId() == null){
            throw new GenericException("Update failed id cannot be null");
        }
        ApiResponse response = service.updatePatientProfile(request, patient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/patient/upToTwo")
    public ResponseEntity<ApiResponse> fetchPatientsWithAgeUpToTwoYears(HttpServletRequest request){
        log.info("REST request to fetch patients with age up to two years");
        ApiResponse response = service.fetchPatientsWithAgeUpToTwoYears(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/patient/export/csv/{patientId}")
    public void exportPatientProfileToCsv(
            HttpServletResponse response,
            @PathVariable Integer patientId,
            HttpServletRequest request
    ) {
        log.info("REST request to export a patient profile to csv {}", patientId);
        Patient patient = service.getPatientProfile(request, patientId);

        response.setContentType("text/csv");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateTimeFormatter.format(LocalDateTime.now());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Patient ID", "FirstName", "LastName", "Age", "Last visit date"};
            String[] nameMapping = {"id", "firstName", "lastName", "age", "lastVisitDate"};

            csvWriter.writeHeader(csvHeader);
            csvWriter.write(patient, nameMapping);
        }catch (Exception e){
            log.error("Failed to export patient profile to csv " + e);
            throw new FailedExportToCsvException("Failed to export patient profile to csv");
        }
    }

    @PostMapping("/patient/{startDateString}/{endDateString}")
    public ResponseEntity<ApiResponse> deletePatientByDateRange(
            @PathVariable String startDateString,
            @PathVariable String endDateString,
            HttpServletRequest request
    ) {
        log.info("REST request to delete patient by date range");

        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        ApiResponse response = service.deletePatientByDateRange(request, startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
