package ru.rschir.hotelsbackend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.rschir.hotelsbackend.dto.ReservationDTO;
import ru.rschir.hotelsbackend.entities.Reservation;
import ru.rschir.hotelsbackend.entities.Room;
import ru.rschir.hotelsbackend.entities.User;
import ru.rschir.hotelsbackend.repositories.ReservationRepository;
import ru.rschir.hotelsbackend.repositories.RoomRepository;
import ru.rschir.hotelsbackend.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findAllByUser(user);
    }

    public Reservation getUserReservationById(Long id, User user) {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id"));
        if (!Objects.equals(reservation.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        return reservation;
    }

    public Reservation createReservation(ReservationDTO reservationDTO, User user) {
        Room room = roomRepository
                .findById(reservationDTO.room_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid room id"));

        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .checkInDate(reservationDTO.check_in_date)
                .checkOutDate(reservationDTO.check_out_date)
                .build();

        if (reservationRepository.existOverlappingReservation(
                reservationDTO.room_id,
                reservationDTO.check_in_date,
                reservationDTO.check_out_date
        )) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room already reserved");
        }

        log.info("adding new reservation");
        return reservationRepository.save(reservation);
    }

    public void deleteReservationById(Long id, User user) {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id"));
        if (!Objects.equals(reservation.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        log.info("removing reservation");
        reservationRepository.deleteById(id);
    }
}
