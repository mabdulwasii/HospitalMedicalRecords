package guru.oze.hospitalmedicalrecords;

import guru.oze.hospitalmedicalrecords.entity.Authority;
import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.entity.enumeration.AuthorityType;
import guru.oze.hospitalmedicalrecords.repository.AuthorityRepository;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HospitalMedicalRecordsApplication.class)
@ExtendWith(SpringExtension.class)
@WithMockUser
class UserServiceTest {
	public static final Integer USER_ID = 3;
    public static final String username = "dola@example.com";
    public static final String PASSWORD = "admin";
    public static final String ENCRYPTED_PASSWORD = "^&**#GEHE&**(((";
    public static final String USERNAME = "admin@example.com";
    public static final String FIRSTNAME = "Ade";
    public static final String LASTNAME = "John";

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
    @Autowired
    private UserService userService;

    StaffInfo staffInfo;

    @BeforeEach
    public void setup() {
        staffInfo = StaffInfo.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .confirmPassword(PASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .registrationDate(LocalDate.now())
                .build();
    }

	@Test
    @DisplayName("Should not create user if passwords mismatch")
    void shouldNotCreateUserIfPasswordMisMatch() {
        var response = userService.registerStaff(staffInfo);
        StaffDto staffDto = (StaffDto) response.getData();
        assertNotNull(staffDto, "User created successfully");
    }

    @Test
    @DisplayName("Should create user if passwords match")
    void shouldCreateUserIfPasswordMatch() {
        var user = new User(USER_ID, username, PASSWORD, true);
        var role_user = new Authority(10, AuthorityType.ROLE_USER);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(authorityRepository.findByName(any())).thenReturn(Optional.of(role_user));
        when(userRepository.save(any())).thenReturn(user);

        var response = userService.registerStaff(staffInfo);
        StaffDto staffDto = (StaffDto) response.getData();

        assertNotNull(staffDto, "User not created");
        assertEquals(staffDto.getUsername(), username);
    }
}
