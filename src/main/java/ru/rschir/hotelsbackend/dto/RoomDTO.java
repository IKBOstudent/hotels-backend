package ru.rschir.hotelsbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import ru.rschir.hotelsbackend.entities.Room;

@AllArgsConstructor
public class RoomDTO {

    @NotNull
    @Min(1)
    public int beds_count;

    @NotNull
    @Min(0)
    public float night_price;

    @NotNull
    public Long hotel_id;

    @NotEmpty
    public Room.RoomCategory roomCategory;
}
