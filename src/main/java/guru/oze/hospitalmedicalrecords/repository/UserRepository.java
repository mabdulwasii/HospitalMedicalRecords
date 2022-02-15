package guru.oze.hospitalmedicalrecords.repository;

import guru.oze.hospitalmedicalrecords.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUuid(String uuid);
    Optional<User> findOneWithAuthoritiesByUsernameIgnoreCase(String username);
    boolean existsByUsername(String username);
    @Query("select user from User user where user.username = ?#{principal.username}")
    User findByUserIsCurrentUser();
}
