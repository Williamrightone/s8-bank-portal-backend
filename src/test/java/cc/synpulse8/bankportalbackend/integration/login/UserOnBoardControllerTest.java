package cc.synpulse8.bankportalbackend.integration.login;

import cc.synpulse8.bankportalbackend.presentation.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserOnBoardControllerTest {

    private static final int REDIS_PORT = 6379;
    private static RedisServer redisServer;

    private static final String LOGIN_URI = "/api/end-user/login";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${client.user-services.url}")
    String userServiceUrl;

    @Mock
    private HashOperations<String, String, Object> hashOperations;

    @BeforeAll
    static void beforeAll() {
        redisServer = new RedisServerBuilder()
                .port(REDIS_PORT)
                .setting("daemonize no")
                .build();
        redisServer.start();
    }

    @BeforeEach
    public void set_rest_response() {

        String sid = "S231101";

        String getUserInfoURL =  userServiceUrl + "/api/end-user/" + sid;

        String mockUserResponse = "{\"data\":{\"sid\":\"S231101\",\"userName\":\"William\",\"passwd\":\"$2a$10$AydPyFBa1BQ6mUYpTDe7DuSWQfAzwlbanwpSHCnp8g1TpKQR3voRa\"}}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Transfer-Encoding", "chunked");
        headers.add("Date", "Wed, 29 Nov 2023 21:08:32 GMT");
        headers.add("Keep-Alive", "timeout=60");
        headers.add("Connection", "keep-alive");

        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockUserResponse, headers, HttpStatus.OK);
        when(restTemplate.getForEntity(getUserInfoURL, String.class)).thenReturn(mockResponseEntity);

        String getUserPermissionsURL =  userServiceUrl + "/api/permission/" + sid;

        String mockPermissionResponse = "{\"data\":[\"ACCOUNTING_VIEW\"]}";

        HttpHeaders permission_headers = new HttpHeaders();
        permission_headers.add("Content-Type", "application/json");
        permission_headers.add("Transfer-Encoding", "chunked");
        permission_headers.add("Date", "Wed, 29 Nov 2023 21:08:32 GMT");
        permission_headers.add("Keep-Alive", "timeout=60");
        permission_headers.add("Connection", "keep-alive");

        ResponseEntity<String> mockPermissionResponseEntity = new ResponseEntity<>(mockPermissionResponse, permission_headers, HttpStatus.OK);
        when(restTemplate.getForEntity(getUserPermissionsURL, String.class)).thenReturn(mockPermissionResponseEntity);

    }

    @Test
    void test_login_success() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSid("S231101");
        loginRequest.setPassword("Demo9731");

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sid").value("S231101"))
                .andExpect(jsonPath("$.data.userName").value("William"));

    }

    @Test
    void  test_login_email_is_null() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("Demo9731");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOGIN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.customErrorCode").value("00001"))
                .andExpect(status().is(400));

    }

    @Test
    void  test_login_passwd_is_null() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSid("S231101");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOGIN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.customErrorCode").value("00001"))
                .andExpect(status().is(400));

    }

    @Test
    void  test_login_passwd_injection() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSid("S231101");
        loginRequest.setPassword("'OR 1=1 --");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOGIN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.customErrorCode").value("00001"))
                .andExpect(status().is(400));

    }

}
