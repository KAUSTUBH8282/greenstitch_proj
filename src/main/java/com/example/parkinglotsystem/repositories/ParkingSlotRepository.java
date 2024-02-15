package com.example.parkinglotsystem.repositories;

import com.example.parkinglotsystem.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> 
{
    List<ParkingSlot> findByIsOccupiedFalse();
    List<ParkingSlot> findByParkedCarColor(String color);
    Optional<ParkingSlot> findByParkedCarRegistrationNumber(String registrationNumber);
    List<ParkingSlot> findByParkingLotIdAndIsOccupiedFalseOrderBySlotNumberAsc(Long parkingLotId);
    Optional<ParkingSlot> findBySlotNumberAndParkingLotId(int slotNumber, Long parkingLotId);
    Optional<ParkingSlot> findByParkedCarRegistrationNumberAndParkingLotId(String registrationNumber, Long parkingLotId);
    List<ParkingSlot> findByParkedCarColorAndParkingLotId(String color, Long parkingLotId);
}