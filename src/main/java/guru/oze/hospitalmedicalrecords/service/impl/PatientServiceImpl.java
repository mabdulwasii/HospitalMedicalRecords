package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import guru.oze.hospitalmedicalrecords.exception.FailedExportToCsvException;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ApiResponse fetchPatientsWithAgeUpToTwoYears(String apiKey) {
        securityUtil.ensureApiKeyIsValid(apiKey);
        List<Patient> patientsAgeLessThanTwo = repository.findAllByAgeLessThanEqual(2);
        log.info("List of patients with age less than 2 {}", patientsAgeLessThanTwo);
        return DtoTransformer.buildApiResponse(patientsAgeLessThanTwo);
    }

    @Override
    public void exportPatientProfileToCsv(Long patientId, String apiKey, HttpServletResponse response) {
        securityUtil.ensureApiKeyIsValid(apiKey);

        Optional<Patient> patientOptional = repository.findById(patientId);

        if (patientOptional.isEmpty()) {
            throw new InvalidPatientException("Invalid patient Id");
        }
        Patient patient = patientOptional.get();

        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(LocalDateTime.now());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Patient ID", "Name", "Age", "Last visit date"};
            String[] nameMapping = {"id", "name", "age", "lastVisitDate"};

            csvWriter.writeHeader(csvHeader);
            csvWriter.write(patient, nameMapping);
        }catch (Exception e){
            log.error("Failed to export patient profile to csv " + e);
            throw new FailedExportToCsvException("Failed to export patient profile to csv");
        }
    }
}
