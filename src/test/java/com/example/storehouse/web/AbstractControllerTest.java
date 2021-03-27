package com.example.storehouse.web;

import com.example.storehouse.config.JwtConfig;
import com.example.storehouse.config.SecurityConfig;
import com.example.storehouse.model.User;
import com.example.storehouse.repository.UsersRepository;
import com.example.storehouse.security.JwtTokenFilter;
import com.example.storehouse.security.JwtTokenProvider;
import com.example.storehouse.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.storehouse.TestData.TEST_ADMIN_EMAIL;
import static com.example.storehouse.TestData.TEST_ADMIN_FIRST_NAME;
import static com.example.storehouse.TestData.TEST_ADMIN_ID;
import static com.example.storehouse.TestData.TEST_ADMIN_LAST_NAME;
import static com.example.storehouse.TestData.TEST_ADMIN_PASSWORD;
import static com.example.storehouse.TestData.TEST_ADMIN_ROLE;
import static com.example.storehouse.TestData.TEST_ADMIN_STATUS;
import static com.example.storehouse.TestData.TEST_USER_EMAIL;
import static com.example.storehouse.TestData.TEST_USER_FIRST_NAME;
import static com.example.storehouse.TestData.TEST_USER_ID;
import static com.example.storehouse.TestData.TEST_USER_LAST_NAME;
import static com.example.storehouse.TestData.TEST_USER_PASSWORD;
import static com.example.storehouse.TestData.TEST_USER_ROLE;
import static com.example.storehouse.TestData.TEST_USER_STATUS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest
@SpringJUnitConfig({
    SecurityConfig.class,
    UserDetailsServiceImpl.class,
    JwtConfig.class,
    JwtTokenFilter.class,
    ControllerExceptionHandler.class,
})
@MockBean({
    UsersRepository.class
})
public abstract class AbstractControllerTest {

    protected static final String AUTH_TOKEN = "jwt-auth-token";

    @Value("${app.jwt.header}")
    String authHeader;

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    protected HttpHeaders headers;
    protected ObjectMapper objectMapper;

    @BeforeEach
    protected void setUp() {
        objectMapper = new ObjectMapper();

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);

        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn(headers.getFirst(authHeader));
        when(jwtTokenProvider.validateToken(AUTH_TOKEN)).thenReturn(true);
    }

    @Test
    protected void contextLoads() {
        assertNotNull(mvc);
        assertNotNull(jwtTokenProvider);
    }

    protected Authentication mockAuthorize(User authorizeAs) {
        return new UsernamePasswordAuthenticationToken(
            new org.springframework.security.core.userdetails.User(
                authorizeAs.getEmail(),
                authorizeAs.getPassword(),
                true,
                true,
                true,
                true,
                authorizeAs.getRole().getAuthorities()
            ),
            null, authorizeAs.getRole().getAuthorities()
        );
    }

    public static User createTestUserAdmin() {
        User user = new User();
        user.setId(TEST_ADMIN_ID);
        user.setEmail(TEST_ADMIN_EMAIL);
        user.setPassword(TEST_ADMIN_PASSWORD);
        user.setFirstName(TEST_ADMIN_FIRST_NAME);
        user.setLastName(TEST_ADMIN_LAST_NAME);
        user.setRole(TEST_ADMIN_ROLE);
        user.setStatus(TEST_ADMIN_STATUS);
        return user;
    }

    public static User createTestUserUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setEmail(TEST_USER_EMAIL);
        user.setPassword(TEST_USER_PASSWORD);
        user.setFirstName(TEST_USER_FIRST_NAME);
        user.setLastName(TEST_USER_LAST_NAME);
        user.setRole(TEST_USER_ROLE);
        user.setStatus(TEST_USER_STATUS);
        return user;
    }
}
