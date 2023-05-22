package com.sdia.radarservice;

import com.sdia.radarservice.entities.Radar;
import com.sdia.radarservice.feign.InfractionRestClient;
import com.sdia.radarservice.feign.RegistrationRestClient;
import com.sdia.radarservice.repository.RadarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class RadarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadarServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            RadarRepository radarRepository,
            InfractionRestClient infractionRestClient,
            RegistrationRestClient registrationRestClient
    ){
        return args -> {
            for(int i = 0; i < 3; i++) {
                Radar radar = Radar.builder()
                .maxSpeed(100.0 + i)
                .latitude(40.0 + i)
                .longitude(10.0 + i)
                .build();
                radarRepository.save(radar);
                System.out.println(radar);
            }

        };
    }
}
