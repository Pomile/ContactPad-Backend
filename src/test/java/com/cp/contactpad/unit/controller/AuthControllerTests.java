package com.cp.contactpad.unit.controller;

import com.cp.contactpad.controller.AuthController;

import com.cp.contactpad.model.User;
import com.cp.contactpad.payload.LoginRequest;
import com.cp.contactpad.payload.SignUpRequest;
import com.cp.contactpad.security.TokenAuthenticationFilter;
import com.cp.contactpad.service.auth.LoginServiceImpl;
import com.cp.contactpad.service.auth.SignUpServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.boot.test.json.JacksonTester;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTests {
    @Autowired private MockMvc mvc;
    @Autowired private WebApplicationContext webApplicationContext;
    @MockBean SignUpServiceImpl signUpService;
    @MockBean LoginServiceImpl loginService;
    @MockBean TokenAuthenticationFilter tokenAuthenticationFilter;
    @MockBean AuthenticationManager authenticationManager;
    private JacksonTester<SignUpRequest> json;
    private JacksonTester<LoginRequest> json2;
    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private User user;
    private final static String TOKEN = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9"+
            ".eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcl9pZCI6MSwiaWF0IjoxNTE2MjM5MDIyfQ"+
            ".FHd3au9S7lPBcevtZ0oFPLdNQ0zR9XHddTRgBQ7efUbjWM5obn5JM03DbxjkGpMwwuijCKDHL7VJBNxh1nYWsA";


    @BeforeAll
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Babatunde");
        signUpRequest.setLastName("Ogedengbe");
        signUpRequest.setGender("male");
        signUpRequest.setEmail("ogedengbe123@gmail");
        signUpRequest.setPassword("criTical@2021");
        signUpRequest.setConfirmPassword("criTical@2021");
        signUpRequest.setMobile_phone("09084445422");
        this.signUpRequest = signUpRequest;

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("ogedengbe123@gmail.com");
        loginRequest.setPassword("criTical@2021");
        this.loginRequest = loginRequest;


        User user = new User();
        user.setUser_id(1L);
        user.setFirstName("Babatunde");
        user.setLastName("Ogedengbe");
        user.setEmail("ogedengbe123@gmail");
        this.user = user;


    }

    @Order(1)
    @DisplayName("it should signup a user")
    @Test
    public void signUp() throws Exception {
        given(signUpService.signUp(any())).willReturn(user);
        mvc.perform(post("/auth/signup")
                .content(json.write(signUpRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User registered successfully@"));

        verify(signUpService, times(1)).signUp(signUpRequest);

    }

    @Order(2)
    @DisplayName("it should not signup a user with invalid password")
    @Test
    public void invalidRegistrationPassword() throws Exception {
        String msg = "Password must contain at least one digit, capital letter, and symbol - # $ %";
        given(signUpService.signUp(any())).willReturn(user);
        this.signUpRequest.setPassword("mummywap");
        this.signUpRequest.setConfirmPassword("mummywap");
        mvc.perform(post("/auth/signup")
                .content(json.write(signUpRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0].password").value(msg));

    }


    @Order(3)
    @DisplayName("it should not signup a user with password mismatch")
    @Test
    public void passwordMismatch() throws Exception {
        String msg = "must match password";
        given(signUpService.signUp(any())).willReturn(user);
        this.signUpRequest.setPassword("criTical@2021");
        this.signUpRequest.setConfirmPassword("mummywap");
        mvc.perform(post("/auth/signup")
                .content(json.write(signUpRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0].confirmPassword").value(msg));

    }

    @Order(4)
    @DisplayName("it should not signup a user with in correct email")
    @Test
    public void invalidRegistrationFirstName() throws Exception {
        String error = "FirstName must not be empty";
        given(signUpService.signUp(any())).willReturn(user);
        this.signUpRequest.setPassword("criTical@2021");
        this.signUpRequest.setConfirmPassword("criTical@2021");
        this.signUpRequest.setFirstName("");
        mvc.perform(post("/auth/signup")
                .content(json.write(signUpRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors[0].firstName").value(error));

    }

    @Order(4)
    @DisplayName("it should log in a user")
    @Test
    public void login() throws Exception {
        given(loginService.login(any())).willReturn(TOKEN);
        mvc.perform(post("/auth/login")
                .content(json2.write(loginRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").value(TOKEN));

        verify(loginService, times(1)).login(loginRequest);
    }

}
