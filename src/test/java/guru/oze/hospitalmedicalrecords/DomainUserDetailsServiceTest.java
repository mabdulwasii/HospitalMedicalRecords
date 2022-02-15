package guru.oze.hospitalmedicalrecords;

import guru.oze.hospitalmedicalrecords.entity.Authority;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.entity.enumeration.AuthorityType;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.security.exception.UserNotActivatedException;
import guru.oze.hospitalmedicalrecords.security.service.DomainUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = HospitalMedicalRecordsApplication.class)
class DomainUserDetailsServiceTest {
	public static final Integer USER_ID = 3;
	public static final String username = "dola@example.com";
	public static final String PASSWORD = "admin";

	private final UserRepository userRepository = mock(UserRepository.class);

	private DomainUserDetailsService domainUserDetailsService;
    private User user;

	@BeforeEach
	void setUp() {
		domainUserDetailsService = new DomainUserDetailsService(userRepository);
        user = new User(USER_ID, username, PASSWORD, true);
        Authority authority = new Authority(1, AuthorityType.ROLE_USER);
        HashSet<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
	}

	@Test
	@DisplayName("Should load user if username is found")
    void shouldLoadUserByUsername() {
        when(userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        var userDetails = domainUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), user.getUsername());
        assertEquals(userDetails.getId(), user.getId());
        assertEquals(userDetails.getPassword(), user.getPassword());


    }

    @Test
    @DisplayName("Should not load user if username is is not found")
    void shouldThrowExceptionIfUsernameNotFound() {
        when(userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> domainUserDetailsService.loadUserByUsername(username));

    }

    @Test
    @DisplayName("Should not load user if username is is not found")
    void shouldThrowExceptionIfUsernameNotActivated() {
        user.setActivated(false);
        when(userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        assertThrows(UserNotActivatedException.class, () -> domainUserDetailsService.loadUserByUsername(username));
    }
}
