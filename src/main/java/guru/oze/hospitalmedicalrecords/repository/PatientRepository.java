package guru.oze.hospitalmedicalrecords.repository;

import guru.oze.hospitalmedicalrecords.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByAgeLessThanEqual(@NonNull Integer age);
    void deleteByLastVisitDateIsBetween(@NonNull LocalDate startDate, @NonNull LocalDate endDate);
}