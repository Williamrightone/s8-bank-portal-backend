package cc.synpulse8.bankportalbackend.service.user;

import cc.synpulse8.bankportalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankportalbackend.domain.service.UserServicesClient;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserServicesClient userServicesClient;

    @Value("${client.user-services.url}")
    String userServiceUrl;

    @Test
    void test_get_user_by_sid() throws Exception {

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

        EndUserInfoRes endUserInfoRes = userServicesClient.getUserInfoBySid(sid);

        assertEquals("S231101", endUserInfoRes.getSid());

    }

    @Test
    void test_get_permissions_by_sid() throws Exception {

        String sid = "S231101";

        String getUserPermissionsURL =  userServiceUrl + "/api/permission/" + sid;

        String mockPermissionResponse = "{\"data\":[\"ACCOUNTING_VIEW\"]}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Transfer-Encoding", "chunked");
        headers.add("Date", "Wed, 29 Nov 2023 21:08:32 GMT");
        headers.add("Keep-Alive", "timeout=60");
        headers.add("Connection", "keep-alive");

        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockPermissionResponse, headers, HttpStatus.OK);
        when(restTemplate.getForEntity(getUserPermissionsURL, String.class)).thenReturn(mockResponseEntity);

        List<String> permissions = userServicesClient.getPermissionsBySid(sid);

        assertEquals(1, permissions.size());

    }

}
