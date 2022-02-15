package guru.oze.hospitalmedicalrecords.repository;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findAllByAgeLessThanEqual(@NonNull Integer age);
    void deleteByLastVisitDateIsBetween(@NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate);
    boolean existsPatientById(Integer id);
}