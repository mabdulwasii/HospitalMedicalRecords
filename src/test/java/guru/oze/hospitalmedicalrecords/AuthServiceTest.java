package guru.oze.hospitalmedicalrecords;

import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@WithMockUser
@SpringBootTest(classes = HospitalMedicalRecordsApplication.class)
class AuthServiceTest {
    public static final String ACCESS_TOKEN = "$%^&&***";
    public static final String REFRESH_TOKEN = "436%%#&*#373883";
    public static final Integer USER_ID = 1;
    public static final String USERNAME = "admin@example.com";
    public static final String PASSWORD = "admin";
    public static final String INVALIDPASSWORD = "yrrjrj3";
    public static final String FIRSTNAME = "Ade";
    public static final String LASTNAME = "John";

    @Autowired
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;


    @MockBean
    private UserService userService;


    @MockBean
    private JWTUtils jwtUtils;

    StaffInfo staffInfo;
    StaffInfo staffInfoWithPasswordMisMatch;

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

        staffInfoWithPasswordMisMatch = StaffInfo.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .confirmPassword(INVALIDPASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .registrationDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("should Register new user")
    void shouldRegisterNewUser() {
	    var user = new User(USER_ID, staffInfo.getUsername(), staffInfo.getPassword(), true);
        ApiResponse response = DtoTransformer.buildApiResponse(user);

        when(userService.registerStaff(staffInfo)).thenReturn(response);

	    var apiResponse = authService.register(staffInfo);

	    assertNotNull(apiResponse);
	    assertEquals(apiResponse.getMessage(), "User created successfully");
    }

    @Test
    @DisplayName("should Throw generic exception if password mismatch")
    void shouldThrowExceptionIfUserNotSaved() {
        when(userService.registerStaff(staffInfoWithPasswordMisMatch)).thenThrow(GenericException.class);
        assertThrows(GenericException.class, () -> authService.register(staffInfo));
    }
}
