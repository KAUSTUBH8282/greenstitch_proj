package com.example.parkinglotsystem.services;

import com.example.parkinglotsystem.entities.Car;
import com.example.parkinglotsystem.entities.ParkingLot;
import com.example.parkinglotsystem.entities.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parkinglotsystem.repositories.ParkingLotRepository;
import com.example.parkinglotsystem.repositories.ParkingSlotRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository,
                              ParkingLotRepository parkingLotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingLotRepository = parkingLotRepository;
    }
    
    
    
   
 // Method to ensure a parking lot exists or create a new one if it doesn't
    private ParkingLot ensureParkingLotExists(Long parkingLotId) {
        return parkingLotRepository.findById(parkingLotId)
                .orElseGet(() -> {
                    ParkingLot newParkingLot = new ParkingLot();
                    newParkingLot.setId(parkingLotId); // Optionally set ID if it's not auto-generated
                    parkingLotRepository.save(newParkingLot);
                    return newParkingLot;
                });
    }

 // Updated method to add parking slots that first ensures the parking lot exists and then updates its capacity
    public void addParkingSlots(Long parkingLotId, int numberOfSlots) {
        // Check if the parking lot exists, create it if it doesn't
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseGet(() -> {
            ParkingLot newParkingLot = new ParkingLot();
            // Assuming auto-generated ID, no need to set it explicitly
            newParkingLot.setCapacity(0); // Initial capacity is set to 0 and will be updated below
            return parkingLotRepository.save(newParkingLot); // Save and return the new parking lot
        });

        // Add the specified number of parking slots to the parking lot
        for (int i = 0; i < numberOfSlots; i++) {
            ParkingSlot slot = new ParkingSlot();
            slot.setParkingLot(parkingLot);
            slot.setIsOccupied(false); // Initially, slots are not occupied
            parkingSlotRepository.save(slot);
        }

        // Update the parking lot's capacity to include the new slots
        // This assumes that the capacity should reflect the total number of slots
        int updatedCapacity = parkingLot.getCapacity() + numberOfSlots;
        parkingLot.setCapacity(updatedCapacity);
        parkingLotRepository.save(parkingLot); // Save the updated parking lot with the new capacity
    }
    
 // Method to delete a parking lot by ID
    public void deleteParkingLot(Long parkingLotId) {
        parkingLotRepository.deleteById(parkingLotId);
    }
    
    // Method to park a car in a specific parking lot
    public Optional<ParkingSlot> parkCar(Long parkingLotId, String registrationNumber, String color) 
    {
        List<ParkingSlot> availableSlots = parkingSlotRepository.findByParkingLotIdAndIsOccupiedFalseOrderBySlotNumberAsc(parkingLotId);

        if (!availableSlots.isEmpty())
        {
            ParkingSlot slot = availableSlots.get(0); // Get the first available slot (nearest to the entry)
            Car car = new Car(registrationNumber, color);
            slot.setParkedCar(car);
            slot.setIsOccupied(true);
            parkingSlotRepository.save(slot);
            return Optional.of(slot);
        } else 
        {
            return Optional.empty(); // No available slots
        }
    }

    // 1. Find available slots for a particular parking lot
    public List<Integer> findAvailableSlotsForParkingLot(Long parkingLotId)
    {
        return parkingSlotRepository.findByParkingLotIdAndIsOccupiedFalseOrderBySlotNumberAsc(parkingLotId)
                .stream()
                .map(ParkingSlot::getSlotNumber)
                .collect(Collectors.toList());
    }
    
    // 2. Get Slot Number for Registration Number for a particular parking lot
    public Optional<Integer> getSlotNumberForRegistrationNumberInParkingLot(String registrationNumber, Long parkingLotId) 
    {
        return parkingSlotRepository.findByParkedCarRegistrationNumberAndParkingLotId(registrationNumber, parkingLotId)
                .map(ParkingSlot::getSlotNumber);
    }

    // Modified functionality to get registration numbers for cars by color in a specific parking lot
    public List<String> getRegistrationNumbersForCarsByColorInParkingLot(String color, Long parkingLotId) {
        return parkingSlotRepository.findByParkedCarColorAndParkingLotId(color, parkingLotId)
                .stream()
                .filter(slot -> slot.getParkedCar() != null)
                .map(slot -> slot.getParkedCar().getRegistrationNumber())
                .collect(Collectors.toList());
    }


    

    // 3. Get Slot Numbers for Cars by Color for a particular parking lot
    public List<Integer> getSlotNumbersForCarsByColorInParkingLot(String color, Long parkingLotId) {
        return parkingSlotRepository.findByParkedCarColorAndParkingLotId(color, parkingLotId)
                .stream()
                .map(ParkingSlot::getSlotNumber)
                .collect(Collectors.toList());
    }
    
    // Method to unpark a car from a given slot in a specific parking lot
    public boolean unparkCar(Long parkingLotId, int slotNumber) {
        // Find the slot by ID and parking lot ID to ensure it belongs to the right parking lot
        Optional<ParkingSlot> optionalSlot = parkingSlotRepository.findBySlotNumberAndParkingLotId(slotNumber, parkingLotId);
        if (optionalSlot.isPresent()) {
            ParkingSlot slot = optionalSlot.get();
            // Check if the slot is currently occupied
            if (slot.getIsOccupied()) {
                // Remove the car from the slot and mark it as available
                slot.setParkedCar(null);
                slot.setIsOccupied(false);
                parkingSlotRepository.save(slot); // Save the changes
                return true; // Unparking successful
            }
        }
        return false; // Slot not found, not occupied, or does not belong to the specified parking lot
    }
}