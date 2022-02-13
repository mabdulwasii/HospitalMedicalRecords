package guru.oze.hospitalmedicalrecords.repository;

import guru.oze.hospitalmedicalrecords.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByUuid(String decrypt);
}