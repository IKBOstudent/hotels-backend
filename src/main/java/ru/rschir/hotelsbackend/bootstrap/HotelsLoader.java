package ru.rschir.hotelsbackend.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rschir.hotelsbackend.entities.Hotel;
import ru.rschir.hotelsbackend.entities.Room;
import ru.rschir.hotelsbackend.entities.User;
import ru.rschir.hotelsbackend.repositories.HotelRepository;
import ru.rschir.hotelsbackend.repositories.RoomRepository;
import ru.rschir.hotelsbackend.repositories.UserRepository;

import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class HotelsLoader implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (hotelRepository.count() == 0) {
            List<Hotel> hotels = bootstrapHotels();
            bootstrapRooms(hotels);
        }

        bootstrapAdmin();
    }

    private List<Hotel> bootstrapHotels() {
        log.info("bootstrapping hotels");
        return hotelRepository.saveAll(List.of(
                Hotel.builder()
                        .name("Hotel cheap")
                        .region("moscow")
                        .starsCount(2)
                        .build(),
                Hotel.builder()
                        .name("Hotel It's okay")
                        .region("moscow")
                        .starsCount(3)
                        .build(),
                Hotel.builder()
                        .name("Hotel Cool")
                        .region("sochi")
                        .starsCount(4)
                        .build(),
                Hotel.builder()
                        .name("Hotel Super good")
                        .region("sochi")
                        .starsCount(5)
                        .build(),
                Hotel.builder()
                        .name("Hotel Four Seasons")
                        .region("moscow")
                        .starsCount(5)
                        .build()
        ));
    }

    private void bootstrapRooms(List<Hotel> hotels) {
        log.info("bootstrapping rooms");
        hotels.forEach(hotel -> roomRepository.saveAll(List.of(
                Room.builder()
                        .hotel(hotel)
                        .bedsCount(2)
                        .nightPrice(1000 * hotel.getStarsCount())
                        .roomCategory(Room.RoomCategory.STANDARD)
                        .build(),
                Room.builder()
                        .hotel(hotel)
                        .bedsCount(2)
                        .nightPrice(2000 * hotel.getStarsCount())
                        .roomCategory(Room.RoomCategory.DELUXE)
                        .build(),
                Room.builder()
                        .hotel(hotel)
                        .bedsCount(4)
                        .nightPrice(3000 * hotel.getStarsCount())
                        .roomCategory(Room.RoomCategory.FAMILY_ROOM)
                        .build()
        )));
    }

    private void bootstrapAdmin() {
        userRepository.save(User.builder()
                                    .publicName("ADMIN")
                                    .username("admin")
                                    .password(passwordEncoder.encode("VerySecureAdminPassword"))
                                    .roles(List.of(User.UserRole.ADMIN))
                                    .build());
    }

}
