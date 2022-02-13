package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface PatientService {
    ApiResponse createPatient(String apiKey, CreatePatientRequest request);
    ApiResponse updatePatientProfile(String apiKey, Patient patient);
    ApiResponse fetchPatientsWithAgeUpToTwoYears(String apiKey);
    void exportPatientProfileToCsv(String apiKey, Long patientId, HttpServletResponse response);
    ApiResponse deletePatientByDateRange(String apiKey, LocalDate startDate, LocalDate endDate);
}
