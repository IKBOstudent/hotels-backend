package ru.rschir.hotelsbackend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rschir.hotelsbackend.dto.HotelDTO;
import ru.rschir.hotelsbackend.entities.Hotel;
import ru.rschir.hotelsbackend.services.HotelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<Hotel> getHotelsByRegion(@Valid @NotNull @RequestParam("region") String region) {
        return hotelService.getHotelsByRegion(region);
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @SecurityRequirement(name = "Authorization")
    public Hotel addHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        return hotelService.addHotel(hotelDTO);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public String deleteHotelById(@PathVariable("id") Long id) {
        hotelService.deleteHotelById(id);
        return "Deleted successfully";
    }
}