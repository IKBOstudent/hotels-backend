package ru.rschir.hotelsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import ru.rschir.hotelsbackend.entities.User;

@AllArgsConstructor
public class UserDTO {

    @NotBlank
    public String public_name;

    @NotBlank
    @Size(min = 3, max = 18)
    public String username;

    @NotBlank
    @Size(min = 5)
    public String password;

    public boolean is_partner;
}
