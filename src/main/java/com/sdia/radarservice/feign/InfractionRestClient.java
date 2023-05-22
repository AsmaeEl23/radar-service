package com.sdia.radarservice.feign;

import com.sdia.radarservice.model.Infraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INFRACTION-SERVICE")
public interface InfractionRestClient {
    @GetMapping(path="/infractions")
    PagedModel<Infraction> pageOfInfractions(@RequestParam(value = "page") int page,@RequestParam(value = "size") int size);
    @GetMapping("/infractions/{id}")
    Infraction getInfractionById(@PathVariable String id);
    @PostMapping("/infractions")
    Infraction save(@RequestBody Infraction infraction);
    @GetMapping("/infractions/radar/{id}")
    List<Infraction> findByRadarId(@PathVariable("id") Long id);




















}
