package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface PatientService {
    ApiResponse createPatient(CreatePatientRequest request);
    ApiResponse updatePatientProfile(Patient patient);
    ApiResponse fetchPatientsWithAgeUpToTwoYears();
    void exportPatientProfileToCsv(Integer patientId, HttpServletResponse response);
    ApiResponse deletePatientByDateRange(LocalDate startDate, LocalDate endDate);
}
