package com.example.storehouse.web;

import com.example.storehouse.dto.AuthenticationRequestTo;
import com.example.storehouse.model.User;
import com.example.storehouse.repository.UsersRepository;
import com.example.storehouse.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.authentication.base_url}")
@Tag(name = "Authentication", description = "JWT Authentication REST API controller")
@RequiredArgsConstructor
@SecurityRequirements
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "Authentication method. Authenticate client by email and password.")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestTo authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword()));
            User user = usersRepository.findByEmail(authRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User does not exists."));
            Map<String, String> authResponse = new HashMap<>();
            authResponse.put("email", authRequest.getEmail());
            authResponse.put("token", jwtTokenProvider.createToken(authRequest.getEmail(), user.getRole().name()));

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    // Что он делает - очищает токен текущего пользователя?
    @GetMapping("/logout")
    @Operation(summary = "Logout method.")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
