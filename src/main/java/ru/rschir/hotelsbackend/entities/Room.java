package ru.rschir.hotelsbackend.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "room_t")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "beds_count")
    @JsonProperty("beds_count")
    private int bedsCount;

    @Column(name = "night_price")
    @JsonProperty("night_price")
    private float nightPrice;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("hotel_id")
    private Hotel hotel;

    @Column(name = "room_category")
    @JsonProperty("room_category")
    private RoomCategory roomCategory;

    public enum RoomCategory {
        STANDARD, DELUXE, FAMILY_ROOM
    }
}
