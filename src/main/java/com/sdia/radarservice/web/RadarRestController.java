package com.sdia.radarservice.web;

import com.sdia.radarservice.entities.Radar;
import com.sdia.radarservice.feign.InfractionRestClient;
import com.sdia.radarservice.feign.RegistrationRestClient;
import com.sdia.radarservice.model.Infraction;
import com.sdia.radarservice.model.Vehicle;
import com.sdia.radarservice.repository.RadarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class RadarRestController {
    private final RadarRepository radarRepository;
    private final RegistrationRestClient registrationRestClient;
    private final InfractionRestClient infractionRestClient;

    public RadarRestController(RadarRepository radarRepository, RegistrationRestClient registrationRestClient, InfractionRestClient infractionRestClient) {
        this.radarRepository = radarRepository;
        this.registrationRestClient = registrationRestClient;
        this.infractionRestClient = infractionRestClient;
    }

    @GetMapping("/radars")
    public List<Radar> radars(){
        return radarRepository.findAll();
    }
    @GetMapping("/radars/{id}")
    public Radar radar(@PathVariable Long id){
        return radarRepository.findById(id).orElseThrow();
    }
    @PostMapping("/radars")
    public Radar saveRadar(@RequestBody Radar radar){
        return radarRepository.save(radar);
    }
    @PutMapping("/radars/{id}")
    public Radar updateRadar(@PathVariable Long id, @RequestBody Radar radar){
        Radar radar1= radarRepository.findById(id).get();
        if(radar.getMaxSpeed() != 0) radar1.setMaxSpeed(radar.getMaxSpeed());
        if(radar.getLatitude() != 0) radar1.setLatitude(radar.getLatitude());
        if(radar.getLongitude() != 0) radar1.setLongitude(radar.getLongitude());

        return radarRepository.save(radar1);
    }

    @DeleteMapping("/radars/{id}")
    public void delete(@PathVariable Long id){
        radarRepository.deleteById(id);
    }

    @PostMapping("/radars/{id}/vehicles/{vehicleId}")
    public Infraction addSpeedInfraction(@PathVariable("id") Long id, @PathVariable("vehicleId") String vehicleId, @RequestParam("speed") Double speed){
            Radar radar = radarRepository.findById(id).get();
            Vehicle vehicle = registrationRestClient.vehicle(vehicleId);
            if (speed > radar.getMaxSpeed()){
                Infraction infraction = Infraction.builder()
                        .id(UUID.randomUUID().toString())
                        .date(new Date())
                        .vehicle_speed(speed)
                        .radar_max_speed(radar.getMaxSpeed())
                        .infraction_amount((speed - radar.getMaxSpeed()) * 100)
                        .radarId(radar.getId())
                        .vehicle_license_plate(vehicle.getId())
                        .vehicle(vehicle)
                        .radar(radar)
                        .build();
                infraction = infractionRestClient.save(infraction);
                return infraction;
            }
        return null;
    }
}
