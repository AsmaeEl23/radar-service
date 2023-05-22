package com.sdia.radarservice.model;

import com.sdia.radarservice.entities.Radar;
import lombok.*;

import java.util.Date;

@ToString
@Data @NoArgsConstructor @AllArgsConstructor  @Builder
public class Infraction {
    private String id;
    private Date date;
    private Long radarId;
    private String vehicle_license_plate;
    private double vehicle_speed;
    private double radar_max_speed;
    private double infraction_amount;
    private Vehicle vehicle;
    private Radar radar;
}
