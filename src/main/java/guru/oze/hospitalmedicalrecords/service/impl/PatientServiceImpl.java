package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.repository.PatientRepository;
import guru.oze.hospitalmedicalrecords.service.PatientService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import guru.oze.hospitalmedicalrecords.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final SecurityUtil securityUtil;

    @Override
    public ApiResponse createPatient(CreatePatientRequest request, String apiKey) {
        securityUtil.ensureApiKeyIsValid(apiKey);
        Patient patient = DtoTransformer.transformCreatePatientRequestToPatientEntity(request);
        Patient createdPatient = repository.save(patient);
        return DtoTransformer.buildApiResponse(createdPatient);
    }

    @Override
    public ApiResponse fetchPatientsAgeUpToTwoYears(String apiKey) {
        securityUtil.ensureApiKeyIsValid(apiKey);
        List<Patient> patientsAgeLessThanTwo = repository.findAllByAgeLessThanEqual(2);
        log.info("List of patients with age less than 2 {}", patientsAgeLessThanTwo);
        return DtoTransformer.buildApiResponse(patientsAgeLessThanTwo);
    }
}
