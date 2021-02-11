package com.example.storehouse.web;

import static com.example.storehouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.example.storehouse.model.User;
import com.example.storehouse.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(ItemsControllerTest.class)
// Я тут с ходу не разобрался, как замокать всю кучу security-зависимостей
// для загрузки только требуемого контекста, поэтому поставил пока загрузку всего
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Value("${app.jwt.header}")
    String authHeader;

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    protected static final String AUTH_TOKEN = "jwt-auth-token";

    protected HttpHeaders headers;
    protected ObjectMapper objectMapper;

    @PostConstruct
    void prepare() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    protected void setUp() {
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
