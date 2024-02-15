package com.example.parkinglotsystem.repositories;
import com.example.parkinglotsystem.entities.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    // Basic CRUD operations are provided by JpaRepository
}