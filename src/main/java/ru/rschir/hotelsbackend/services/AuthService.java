package ru.rschir.hotelsbackend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.rschir.hotelsbackend.dto.AuthResponseDTO;
import ru.rschir.hotelsbackend.security.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO getAccessToken(String username, String password) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);

            String token = jwtProvider.generateToken(userService.loadUserByUsername(username));
            return new AuthResponseDTO(token);
        } catch (BadCredentialsException e) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
