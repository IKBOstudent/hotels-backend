package ru.rschir.hotelsbackend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.rschir.hotelsbackend.dto.RoomDTO;
import ru.rschir.hotelsbackend.entities.Hotel;
import ru.rschir.hotelsbackend.entities.Room;
import ru.rschir.hotelsbackend.repositories.HotelRepository;
import ru.rschir.hotelsbackend.repositories.RoomRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public List<Room> getRooms(
            Long hotelId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int guestsCount
    ) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id");
        }
        return roomRepository.findAllFree(hotelId, checkInDate, checkOutDate, guestsCount);
    }

    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id"));
    }

    public Room addRoom(RoomDTO roomDTO) {
        Hotel hotel = hotelRepository
                .findById(roomDTO.hotel_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid hotel id"));

        Room room = Room.builder()
                .hotel(hotel)
                .bedsCount(roomDTO.beds_count)
                .nightPrice(roomDTO.night_price)
                .build();

        log.info("adding new room");
        return roomRepository.save(room);
    }

    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id");
        }
        log.info("removing room");
        roomRepository.deleteById(id);
    }
}

