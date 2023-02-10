package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.poem.model.dto.AuthenticationRequestDto;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/auth")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDto requestDto) {

        String login = requestDto.getUsername();
        String password = requestDto.getPassword();
        Authentication authRequest = new UsernamePasswordAuthenticationToken(
                login,
                password
        );

        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
