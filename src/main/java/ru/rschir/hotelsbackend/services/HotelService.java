package ru.rschir.hotelsbackend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.rschir.hotelsbackend.dto.HotelDTO;
import ru.rschir.hotelsbackend.entities.Hotel;
import ru.rschir.hotelsbackend.repositories.HotelRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> getHotelsByRegion(String region) {
        return hotelRepository.findAllByRegion(region);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id"));
    }

    public Hotel addHotel(HotelDTO hotelDTO) {
        Hotel hotel = Hotel.builder()
                .name(hotelDTO.name)
                .region(hotelDTO.region)
                .starsCount(hotelDTO.stars_count)
                .build();

        log.info("adding new hotel");
        return hotelRepository.save(hotel);
    }

    public void deleteHotelById(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id");
        }
        log.info("removing hotel");
        hotelRepository.deleteById(id);
    }
}
