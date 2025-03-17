package com.foodDelivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(nullable = false, length = 100)
    private String cityName;

    @Column(nullable = false, length = 100)
    private String stationName;

    @Column(nullable = false, length = 50)
    private String wmoCode;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<WeatherData> weatherData;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<BaseFee> baseFees;
}
