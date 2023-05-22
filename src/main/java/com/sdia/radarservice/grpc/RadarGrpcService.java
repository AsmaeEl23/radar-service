package com.sdia.radarservice.grpc;

import com.google.protobuf.Empty;
import com.sdia.radarservice.feign.InfractionRestClient;
import com.sdia.radarservice.feign.RegistrationRestClient;
import com.sdia.radarservice.grpc.stubs.Radar;
import com.sdia.radarservice.grpc.stubs.RadarServiceGrpc;
import com.sdia.radarservice.model.Infraction;
import com.sdia.radarservice.model.Vehicle;
import com.sdia.radarservice.repository.RadarRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;

@GrpcService
@EnableFeignClients
public class RadarGrpcService extends RadarServiceGrpc.RadarServiceImplBase {

    private final RadarRepository radarRepository;
    private final InfractionRestClient infractionRestClient;
    private final RegistrationRestClient registrationRestClient;

    public RadarGrpcService(RadarRepository radarRepository, InfractionRestClient infractionRestClient, RegistrationRestClient registrationRestClient) {
        this.radarRepository = radarRepository;
        this.infractionRestClient = infractionRestClient;
        this.registrationRestClient = registrationRestClient;
    }


    @Override
    public void detectInfraction(Radar.DetectRequest request, StreamObserver<Empty> responseObserver) {
        Long radarId = request.getRadarId();
        String vehicleId = request.getVehicleId();
        Double vehicleSpeed = request.getSpeed();

        System.out.println("radar  id "+radarId+"v id"+vehicleId+" c speed"+vehicleSpeed);
        com.sdia.radarservice.entities.Radar radar = radarRepository.findById(radarId).orElseThrow();
        System.out.println("Radar "+radar.toString());
        Vehicle vehicle = registrationRestClient.vehicle(vehicleId);
        System.out.println("vehicle "+vehicle.toString());
        if (vehicleSpeed > radar.getMaxSpeed()) {
            Infraction infraction = Infraction.builder()
                    .id(null)
                    .date(new Date())
                    .vehicle_speed(vehicleSpeed)
                    .radar_max_speed(radar.getMaxSpeed())
                    .infraction_amount((vehicleSpeed - radar.getMaxSpeed()) * 100)
                    .radarId(radar.getId())
                    .vehicle_license_plate(vehicle.getId())
                    .vehicle(vehicle)
                    .radar(radar)
                    .build();
            infraction = infractionRestClient.save(infraction);
            System.out.println(infraction.toString());

        }
    }
}

