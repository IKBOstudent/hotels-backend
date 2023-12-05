package ru.rschir.hotelsbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rschir.hotelsbackend.entities.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT a " +
            "FROM Room a " +
            "WHERE a.hotel.id = :hotelId " +
            "AND a.bedsCount >= :guestsCount " +
            "AND ( " +
            "       SELECT COUNT(b) " +
            "       FROM Reservation b " +
            "       WHERE b.room.id = :roomId " +
            "       AND b.checkInDate < :checkOutDate " +
            "       AND b.checkOutDate > :checkInDate " +
            "   ) = 0 ")
    List<Room> findAllFree(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate, int guestsCount);
}
