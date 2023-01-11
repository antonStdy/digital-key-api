package com.ispk.digitalkeyapi.auth;

import com.ispk.digitalkeyapi.auth.dto.RegistrationCredentialsDto;
import com.ispk.digitalkeyapi.auth.dto.LoginCredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/login")
    AuthToken authenticate(@RequestBody LoginCredentialsDto creds) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(creds.getEmail());

        return new AuthToken(token);
    }

    @PostMapping("/register")
    AuthToken register(@RequestBody @Valid RegistrationCredentialsDto registrationCredentialsDto) {
        var user = authService.registerUser(registrationCredentialsDto);
        String token = jwtUtil.generateToken(user.getAccount().getEmail());
        return new AuthToken(token);
    }

    @Value
    private static class AuthToken {
        String token;
    }

}
