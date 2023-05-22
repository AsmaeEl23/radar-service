package com.sdia.radarservice.feign;

import com.sdia.radarservice.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(name = "REGISTRATION-SERVICE")
public interface RegistrationRestClient {

    @GetMapping(path = "/vehicles/{id}")
    Vehicle vehicle(@PathVariable(name = "id") String id);

    @GetMapping(path = "/owners/{id}")
    Vehicle getOwnerById(@PathVariable(name = "id") Long id);

    @PostMapping("/vehicles")
    Vehicle addVehicle(@RequestBody Vehicle vehicle);

    @GetMapping(path = "/vehicles")
    Collection<Vehicle> vehicles();
}
