package com.sdia.radarservice.repository;

import com.sdia.radarservice.entities.Radar;
import com.sdia.radarservice.model.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestResource
public interface RadarRepository extends JpaRepository<Radar,Long> {
}
