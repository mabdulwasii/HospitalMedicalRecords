package guru.oze.hospitalmedicalrecords.repository;

import guru.oze.hospitalmedicalrecords.entity.Authority;
import guru.oze.hospitalmedicalrecords.entity.enumeration.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityType name);
}
