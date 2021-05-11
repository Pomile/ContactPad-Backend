package com.cp.contactpad.integration;

import com.cp.contactpad.payload.ApiResponse;
import org.flywaydb.core.Flyway;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    Flyway flyway;
    private HttpHeaders  httpHeaders;
    private JSONObject UserObj;
    private String url;



    @BeforeAll
    public void setUp() throws JSONException {
        flyway.clean();
        flyway.migrate();
        this.url = "http://localhost:" + port + "/auth/signup";
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.UserObj = new JSONObject();
        UserObj.put("firstName", "Babatunde");
        UserObj.put("lastName", "Alex");
        UserObj.put("email", "ogedengbe123@gmail.com");
        UserObj.put("gender", "male");
        UserObj.put("mobile_phone", "09085554522");
        UserObj.put("password", "passOword@2021");
        UserObj.put("confirmPassword", "passOword@2021");

    }

    @Order(1)
    @Test
    public void registerUser() {
        HttpEntity<String> request =
                new HttpEntity<String>(UserObj.toString(), httpHeaders);
        ApiResponse response = restTemplate.postForObject(url, request, ApiResponse.class);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("User registered successfully@", response.getMessage());
    }


}
