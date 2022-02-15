package guru.oze.hospitalmedicalrecords;

import guru.oze.hospitalmedicalrecords.config.RSAKeyConfigProperties;
import guru.oze.hospitalmedicalrecords.entity.RefreshToken;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.security.exception.TokenRefreshExpiredException;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.RefreshTokenService;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenRequest;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenResponse;
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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    private RefreshTokenService refreshTokenService;

    @MockBean
    private UserService userService;


    @MockBean
    private JWTUtils jwtUtils;

    @MockBean
    private RSAKeyConfigProperties rsaKeyProp;

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

    @Test
    @DisplayName("should Refresh token if token has not expired")
    void shouldRefreshTokenIfNotExpired() {
	    var user = new User(USER_ID, USERNAME, PASSWORD, true);
	    var refreshToken = new RefreshToken(1L, user, REFRESH_TOKEN, Instant.now().plus(Duration.ofMillis(2000)));

	    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(REFRESH_TOKEN);

	    when(refreshTokenService.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
	    when(refreshTokenService.verifyExpiration(any())).thenReturn(refreshToken);
	    when(jwtUtils.generateToken(any(), any())).thenReturn(ACCESS_TOKEN);

	    ApiResponse response = authService.refreshToken(refreshTokenRequest);
        RefreshTokenResponse refreshTokenResponse = (RefreshTokenResponse)response.getData();

        assertNotNull(refreshTokenResponse);
	    assertEquals(refreshTokenResponse.getRefreshToken(), REFRESH_TOKEN);
	    assertEquals(refreshTokenResponse.getTokenType(), "Bearer");
	    assertEquals(refreshTokenResponse.getAccessToken(), ACCESS_TOKEN);
    }

    @Test
    @DisplayName("should throw exception if token has expired")
    void shouldThrowExceptionIfTokenExpired() {

        var user = new User(USER_ID, USERNAME, PASSWORD, true);
        var refreshToken = new RefreshToken(1L, user, REFRESH_TOKEN, Instant.now().plus(Duration.ofMillis(2000)));

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(REFRESH_TOKEN);

        when(refreshTokenService.findByToken(REFRESH_TOKEN)).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(any())).thenThrow(TokenRefreshExpiredException.class);
        when(jwtUtils.generateToken(any(), any())).thenReturn(ACCESS_TOKEN);

        assertThrows(TokenRefreshExpiredException.class, () -> authService.refreshToken(refreshTokenRequest));
    }
}
