package com.example.storehouse.web;

import com.example.storehouse.dto.AuthenticationRequestTo;
import com.example.storehouse.model.User;
import com.example.storehouse.repository.UsersRepository;
import com.example.storehouse.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.authentication.base_url}")
@Tag(name = "Authentication", description = "JWT Authentication REST API controller")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private UsersRepository usersRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(
        AuthenticationManager authenticationManager,
        UsersRepository usersRepository,
        JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @Operation(summary = "Authentication method. Authenticate client by email and password.")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestTo authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                                                        authRequest.getPassword()));
            User user = usersRepository.findByEmail(authRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User does not exists."));
            String token = jwtTokenProvider
                .createToken(authRequest.getEmail(), user.getRole().name());
            Map<Object, Object> authResponse = new HashMap<>();
            authResponse.put("email", authRequest.getEmail());
            authResponse.put("token", token);

            return ResponseEntity.ok(authResponse);
        }
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout method.")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
