package ru.rschir.hotelsbackend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rschir.hotelsbackend.dto.RoomDTO;
import ru.rschir.hotelsbackend.entities.Room;
import ru.rschir.hotelsbackend.services.RoomService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<Room> getRooms(
            @Valid @NotNull @RequestParam("hotel_id") Long hotelId,
            @Valid @NotNull @RequestParam("check_in_date") LocalDate checkInDate,
            @Valid @NotNull @RequestParam("check_out_date") LocalDate checkOutDate,
            @Valid @NotNull @RequestParam("guests_count") int guestsCount
    ) {
        return roomService.getRooms(hotelId, checkInDate, checkOutDate, guestsCount);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable("id") Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    @SecurityRequirement(name = "Authorization")
    public Room addRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.addRoom(roomDTO);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public String deleteRoomById(@PathVariable("id") Long id) {
        roomService.deleteRoomById(id);
        return "Deleted successfully";
    }
}
