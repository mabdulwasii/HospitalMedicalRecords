package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface PatientService {
    ApiResponse createPatient(HttpServletRequest request, CreatePatientRequest createPatientRequest);
    ApiResponse updatePatientProfile(HttpServletRequest request, Patient patient);
    ApiResponse fetchPatientsWithAgeUpToTwoYears(HttpServletRequest request);
    Patient getPatientProfile(HttpServletRequest request, Integer patientId);
    ApiResponse deletePatientByDateRange(HttpServletRequest request, LocalDate startDate, LocalDate endDate);
}
