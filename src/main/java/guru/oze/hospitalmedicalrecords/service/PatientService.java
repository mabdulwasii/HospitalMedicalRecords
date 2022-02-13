package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;

import javax.servlet.http.HttpServletResponse;

public interface PatientService {
    ApiResponse createPatient(CreatePatientRequest request, String apiKey);
    ApiResponse fetchPatientsWithAgeUpToTwoYears(String apiKey);
    void exportPatientProfileToCsv(Long patientId, String apiKey, HttpServletResponse response);
}
