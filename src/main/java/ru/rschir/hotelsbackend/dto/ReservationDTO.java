package ru.rschir.hotelsbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
public class ReservationDTO {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate check_in_date;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate check_out_date;

    @NotNull
    public Long room_id;
}
