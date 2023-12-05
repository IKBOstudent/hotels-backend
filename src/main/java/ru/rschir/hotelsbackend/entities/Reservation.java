package ru.rschir.hotelsbackend.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservation_t")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "check_in_date")
    @JsonProperty("check_in_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @NotNull
    @Column(name = "check_out_date")
    @JsonProperty("check_out_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("room_id")
    private Room room;

    @JsonProperty("hotel_id")
    public Long getHotelId() {
        return room.getHotel().getId();
    }

    @JsonProperty("nights_count")
    public long getNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @JsonProperty("total_sum")
    public float getTotalSum() {
        return room.getNightPrice() * getNights();
    }
}