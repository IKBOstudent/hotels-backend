package ru.rschir.hotelsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class AuthRequestDTO {

    @NotBlank
    @Size(min = 3, max = 18)
    public String username;

    @NotBlank
    @Size(min = 5)
    public String password;
}
