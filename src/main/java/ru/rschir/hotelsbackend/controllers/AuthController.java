package ru.rschir.hotelsbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rschir.hotelsbackend.dto.AuthRequestDTO;
import ru.rschir.hotelsbackend.dto.AuthResponseDTO;
import ru.rschir.hotelsbackend.dto.UserDTO;
import ru.rschir.hotelsbackend.services.AuthService;
import ru.rschir.hotelsbackend.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return authService.getAccessToken(authRequestDTO.username, authRequestDTO.password);
    }

    @PostMapping("/register")
    public AuthResponseDTO createNewUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return authService.getAccessToken(userDTO.username, userDTO.password);
    }

}
