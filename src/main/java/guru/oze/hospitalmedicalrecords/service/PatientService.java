package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;

public interface PatientService {
    ApiResponse createPatient(CreatePatientRequest request, String apiKey);
    ApiResponse fetchPatientsAgeUpToTwoYears(String apiKey);
}
