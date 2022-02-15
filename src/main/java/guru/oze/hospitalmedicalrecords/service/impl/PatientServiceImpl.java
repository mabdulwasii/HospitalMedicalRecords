package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.exception.InvalidPatientException;
import guru.oze.hospitalmedicalrecords.repository.PatientRepository;
import guru.oze.hospitalmedicalrecords.service.PatientService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.CreatePatientRequest;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import guru.oze.hospitalmedicalrecords.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final SecurityUtil securityUtil;

    @Override
    public ApiResponse createPatient(HttpServletRequest request, CreatePatientRequest createPatientRequest) {
        securityUtil.ensureApiKeyIsValid(request);
        Patient patient = DtoTransformer.transformCreatePatientRequestToPatientEntity(createPatientRequest);
        Patient createdPatient = repository.save(patient);
        return DtoTransformer.buildApiResponse(createdPatient);
    }

    @Override
    public ApiResponse updatePatientProfile(HttpServletRequest request, Patient patient) {
        securityUtil.ensureApiKeyIsValid(request);
        if(repository.existsPatientById(patient.getId())){
            throw new GenericException("Invalid patient id");
        }
        Patient updatedProfile = repository.save(patient);
        log.info("Update patient profile {} " , updatedProfile);
        return DtoTransformer.buildApiResponse(updatedProfile);
    }

    @Override
    public ApiResponse fetchPatientsWithAgeUpToTwoYears(HttpServletRequest request) {
        securityUtil.ensureApiKeyIsValid(request);
        List<Patient> patientsAgeLessThanTwo = repository.findAllByAgeLessThanEqual(2);
        log.info("List of patients with age less than 2 {}", patientsAgeLessThanTwo);
        return DtoTransformer.buildApiResponse(patientsAgeLessThanTwo);
    }

    @Override
    public Patient getPatientProfile(HttpServletRequest request, Integer patientId) {
        securityUtil.ensureApiKeyIsValid(request);

        Optional<Patient> patientOptional = repository.findById(patientId);

        if (patientOptional.isEmpty()) {
            throw new InvalidPatientException("Invalid patient Id");
        }
        return patientOptional.get();
    }

    @Override
    public ApiResponse deletePatientByDateRange(HttpServletRequest request, LocalDate startDate, LocalDate endDate) {
        securityUtil.ensureApiKeyIsValid(request);
        repository.deleteByLastVisitDateIsBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return DtoTransformer.buildApiResponse("Patient profile(s) deleted successfully");
    }
}
