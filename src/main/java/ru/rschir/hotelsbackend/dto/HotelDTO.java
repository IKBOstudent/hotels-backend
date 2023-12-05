package ru.rschir.hotelsbackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class HotelDTO {

    @NotBlank
    public String name;

    @NotBlank
    public String region;

    @Max(5)
    @Min(1)
    public int stars_count;
}
