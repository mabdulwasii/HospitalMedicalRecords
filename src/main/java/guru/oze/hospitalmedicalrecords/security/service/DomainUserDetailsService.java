package guru.oze.hospitalmedicalrecords.security.service;

import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.security.exception.UserNotActivatedException;
import guru.oze.hospitalmedicalrecords.security.jwt.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
@AllArgsConstructor
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);

        User loginUser = userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));

        if (!loginUser.isActivated()) {
            throw new UserNotActivatedException("User " + username + " was not activated");
        }
        return UserDetailsImpl.build(loginUser);
    }

}
