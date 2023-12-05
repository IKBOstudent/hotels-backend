package ru.rschir.hotelsbackend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rschir.hotelsbackend.dto.ReservationDTO;
import ru.rschir.hotelsbackend.entities.Reservation;
import ru.rschir.hotelsbackend.entities.User;
import ru.rschir.hotelsbackend.services.ReservationService;
import ru.rschir.hotelsbackend.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping
    public List<Reservation> getUserReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());
        return reservationService.getUserReservations(user);
    }

    @GetMapping("/{id}")
    public Reservation getUserReservationById(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());
        return reservationService.getUserReservationById(id, user);
    }

    @PostMapping
    public Reservation createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());
        return reservationService.createReservation(reservationDTO, user);
    }

    @DeleteMapping("/{id}")
    public String deleteReservationById(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());
        reservationService.deleteReservationById(id, user);
        return "Deleted successfully";
    }
}