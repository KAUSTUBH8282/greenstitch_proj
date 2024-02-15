package com.example.parkinglotsystem.controllers;

import com.example.parkinglotsystem.entities.Car;
import com.example.parkinglotsystem.entities.ParkingSlot;
import com.example.parkinglotsystem.services.ParkingSlotService;

//import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @Autowired
    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

 // 1. Endpoint to get available slots for a particular parking lot
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSlotsForParkingLot(@RequestParam Long parkingLotId) {
        List<Integer> availableSlots = parkingSlotService.findAvailableSlotsForParkingLot(parkingLotId);
        
        if (availableSlots.isEmpty()) {
            return ResponseEntity.ok("Zero slots available.");
        } else {
            return ResponseEntity.ok(availableSlots);
        }
    }

 // Modified endpoint to get registration numbers for cars by color in a specific parking lot
    @GetMapping("/cars/registration-numbers")
    public ResponseEntity<List<String>> getRegistrationNumbersForCarsByColorInParkingLot(
    		@RequestParam String color,
            @RequestParam Long parkingLotId) {
        List<String> registrationNumbers = parkingSlotService.getRegistrationNumbersForCarsByColorInParkingLot(color, parkingLotId);
        return ResponseEntity.ok(registrationNumbers);
    }

    // 2. Endpoint to get slot number for registration number in a specific parking lot
    @GetMapping("/cars/slots")
    public ResponseEntity<?> getSlotNumberForRegistrationNumberInParkingLot(
            @RequestParam String registrationNumber,
            @RequestParam Long parkingLotId) {
        Optional<Integer> slotNumber = parkingSlotService.getSlotNumberForRegistrationNumberInParkingLot(registrationNumber, parkingLotId);
        return slotNumber
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

 // 3. Endpoint to get slot numbers for cars by color in a specific parking lot
    @GetMapping("/cars/colors/slots")
    public ResponseEntity<List<Integer>> getSlotNumbersForCarsByColorInParkingLot(
            @RequestParam String color,
            @RequestParam Long parkingLotId) {
        List<Integer> slotNumbers = parkingSlotService.getSlotNumbersForCarsByColorInParkingLot(color, parkingLotId);
        return ResponseEntity.ok(slotNumbers);
    }
    
    @PostMapping("/add-slots")
    public ResponseEntity<?> addParkingSlots(@RequestParam Long parkingLotId,
                                             @RequestParam int numberOfSlots)
    {
        parkingSlotService.addParkingSlots(parkingLotId, numberOfSlots);
        return ResponseEntity.ok("Added " + numberOfSlots + " slots to parking lot " + parkingLotId);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteParkingLot(@RequestParam Long parkingLotId) {
        try {
            parkingSlotService.deleteParkingLot(parkingLotId);
            return ResponseEntity.ok("Parking lot " + parkingLotId + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Failed to delete parking lot " + parkingLotId + ". " + e.getMessage());
        }
    }

    @PostMapping("/park")
    public ResponseEntity<?> parkCar(@RequestParam Long parkingLotId, @RequestBody Car car) {
        Optional<ParkingSlot> parkingSlot = parkingSlotService.parkCar(parkingLotId,
                                                                      car.getRegistrationNumber(),
                                                                      car.getColor());
        return parkingSlot.map(slot -> ResponseEntity.ok("Allocated slot number: " + slot.getSlotNumber()))
                          .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                         .body("Sorry, parking lot " + parkingLotId + " is full"));
    }

    @PostMapping("/unpark")
    public ResponseEntity<?> unparkCar(@RequestParam Long parkingLotId, @RequestParam int slotNumber) {
        boolean success = parkingSlotService.unparkCar(parkingLotId, slotNumber);
        if (success) {
            return ResponseEntity.ok("Car unparked successfully from slot " + slotNumber + " in parking lot " + parkingLotId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unpark car from slot " + slotNumber + " in parking lot " + parkingLotId + ". Slot may not be occupied or does not exist.");
        }
    }

}
