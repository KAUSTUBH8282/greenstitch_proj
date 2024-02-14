package services;

import entities.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ParkingSlotRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    @Autowired
    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public List<ParkingSlot> findAvailableSlots() {
        return parkingSlotRepository.findByIsOccupiedFalse();
    }

    public List<ParkingSlot> findSlotsByCarColor(String color) {
        return parkingSlotRepository.findByParkedCarColor(color);
    }

    public Optional<ParkingSlot> findSlotByCarRegistrationNumber(String registrationNumber) {
        return parkingSlotRepository.findByParkedCarRegistrationNumber(registrationNumber);
    }

    // New functionality
    public List<String> getRegistrationNumbersForCarsByColor(String color) {
        return parkingSlotRepository.findByParkedCarColor(color).stream()
                .filter(slot -> slot.getParkedCar() != null)
                .map(slot -> slot.getParkedCar().getRegistrationNumber())
                .collect(Collectors.toList());
    }

    public Integer getSlotNumberForRegistrationNumber(String registrationNumber) {
        return parkingSlotRepository.findByParkedCarRegistrationNumber(registrationNumber)
                .map(ParkingSlot::getSlotNumber)
                .orElse(null); // Return null or handle as per requirement if not found
    }

    public List<Integer> getSlotNumbersForCarsByColor(String color) {
        return parkingSlotRepository.findByParkedCarColor(color).stream()
                .map(ParkingSlot::getSlotNumber)
                .collect(Collectors.toList());
    }
}
