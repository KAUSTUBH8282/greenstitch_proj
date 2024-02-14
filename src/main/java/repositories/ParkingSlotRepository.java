package repositories;

import entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> 
{
    List<ParkingSlot> findByIsOccupiedFalse();
    List<ParkingSlot> findByParkedCarColor(String color);
    Optional<ParkingSlot> findByParkedCarRegistrationNumber(String registrationNumber);
}