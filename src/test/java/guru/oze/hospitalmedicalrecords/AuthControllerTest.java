package guru.oze.hospitalmedicalrecords;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.constant.ResponseCode;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.Jwt;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalMedicalRecordsApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class AuthControllerTest {
    public static final String ACCESS_TOKEN = "$%^&&***";
    public static final String REFRESH_TOKEN = "436%%#&*#373883";
    public static final Integer USER_ID = 1;
    public static final String username = "user@example.com";
	public static final String PASSWORD = "Admin1342525#";
	public static final String INVALIDPASSWORD = "admin1342525#";
    public static final String USERNAME = "admin@example.com";
    public static final String FIRSTNAME = "Ade";
    public static final String LASTNAME = "John";

	@MockBean
	private AuthService authService;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

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
	@DisplayName("Should signup if username does not exist")
	void shouldSignupWithValidUsername() throws Exception {
		var apiResponse = ApiResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message("User created successfully")
                        .build();

		when(authService.register(staffInfo)).thenReturn(apiResponse);

		//execute the post request
		mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(staffInfo)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should not signup if email is invalid")
    void shouldNotSignupIfEmailIsInvalid() {

	    when(authService.register(staffInfo)).thenThrow(RuntimeException.class);

	    //execute the post request

	    Assertions.assertThrows(AssertionError.class,
                () -> mockMvc.perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON)
					    .content(asJsonString(staffInfo)))

			    .andExpect(status().is5xxServerError()).andExpect(content().contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    @DisplayName("Should sign in registered user")
    void shouldSignInIfRegisteredUser() throws Exception {

        var loginDetails = new LoginDetails(username, PASSWORD);
        var jwt = new Jwt(ACCESS_TOKEN, REFRESH_TOKEN, USER_ID, username, new ArrayList<>());
        ApiResponse response = DtoTransformer.buildApiResponse(jwt);

        when(authService.authenticate(loginDetails)).thenReturn(response);

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDetails)))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object object) {
        try {
             return new ObjectMapper().findAndRegisterModules().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
