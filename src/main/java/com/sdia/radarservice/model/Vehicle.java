package com.sdia.radarservice.model;

import lombok.*;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    private String id;
    private String licensePlateNumber;
    private String brand;
    private double fiscalPower;
    private String model;
    private Owner owner;
}
