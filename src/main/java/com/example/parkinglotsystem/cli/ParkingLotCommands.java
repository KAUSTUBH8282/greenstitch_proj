package com.example.parkinglotsystem.cli;

import com.example.parkinglotsystem.entities.Car;
import com.example.parkinglotsystem.entities.ParkingSlot;
import com.example.parkinglotsystem.services.ParkingSlotService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ParkingLotCommands {

    private final ParkingSlotService parkingSlotService;

    @Autowired
    public ParkingLotCommands(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @ShellMethod("Create a parking lot with a specified number of slots and return the ID.")
    public String createParkingLot(@ShellOption int numberOfSlots) {
        Long parkingLotId = parkingSlotService.addParkingSlots(numberOfSlots);
        return String.format("Created a parking lot with ID %d and %d slots", parkingLotId, numberOfSlots);
    }

    @ShellMethod("Park a car in the parking lot.")
    public String parkCar(@ShellOption long parkingLotId, @ShellOption String registrationNumber, @ShellOption String color) {
        var slot = parkingSlotService.parkCar(parkingLotId, registrationNumber, color);
        return slot.isPresent() ? "Allocated slot number: " + slot.get().getSlotNumber() : "Sorry, parking lot is full";
    }

    @ShellMethod("Unpark a car from a slot.")
    public String unparkCar(@ShellOption long parkingLotId, @ShellOption int slotNumber) {
        boolean success = parkingSlotService.unparkCar(parkingLotId, slotNumber);
        return success ? "Slot number " + slotNumber + " is free" : "Operation failed";
    }

    // Add more commands as necessary
    @ShellMethod("Get available slots for a specific parking lot.")
    public String getAvailableSlotsForParkingLot(@ShellOption Long parkingLotId) {
        List<Integer> availableSlots = parkingSlotService.findAvailableSlotsForParkingLot(parkingLotId);
        return availableSlots.isEmpty() ? "Zero slots available." : availableSlots.toString();
    }

    @ShellMethod("Get registration numbers for cars by color in a specific parking lot.")
    public String getRegistrationNumbersForCarsByColorInParkingLot(@ShellOption String color, @ShellOption Long parkingLotId) {
        List<String> registrationNumbers = parkingSlotService.getRegistrationNumbersForCarsByColorInParkingLot(color, parkingLotId);
        return registrationNumbers.isEmpty() ? "No cars found with color " + color : String.join(", ", registrationNumbers);
    }

    @ShellMethod("Get slot number for a car's registration number in a specific parking lot.")
    public String getSlotNumberForRegistrationNumberInParkingLot(@ShellOption String registrationNumber, @ShellOption Long parkingLotId) {
        Optional<Integer> slotNumber = parkingSlotService.getSlotNumberForRegistrationNumberInParkingLot(registrationNumber, parkingLotId);
        return slotNumber.map(number -> "Slot number: " + number).orElse("Not found.");
    }

    @ShellMethod("Get slot numbers for cars by color in a specific parking lot.")
    public String getSlotNumbersForCarsByColorInParkingLot(@ShellOption String color, @ShellOption Long parkingLotId) {
        List<Integer> slotNumbers = parkingSlotService.getSlotNumbersForCarsByColorInParkingLot(color, parkingLotId);
        return slotNumbers.isEmpty() ? "No slots found for cars with color " + color : slotNumbers.toString();
    }

    @ShellMethod("Delete a parking lot.")
    public String deleteParkingLot(@ShellOption Long parkingLotId) {
        try {
            parkingSlotService.deleteParkingLot(parkingLotId);
            return "Parking lot " + parkingLotId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete parking lot " + parkingLotId + ". " + e.getMessage();
        }
    }

    // You might need to create a helper method to convert a car JSON to a Car object for the park command.
    // Assuming a simple Car constructor for demonstration.
    @ShellMethod("Park a car into a parking lot.")
    public String parkCarShell(@ShellOption Long parkingLotId, @ShellOption String registrationNumber, @ShellOption String color) {
        Car car = new Car(registrationNumber, color); // Simplified, you may need to adjust based on your Car entity constructor.
        Optional<ParkingSlot> parkingSlot = parkingSlotService.parkCar(parkingLotId, car.getRegistrationNumber(), car.getColor());
        return parkingSlot.map(slot -> "Allocated slot number: " + slot.getSlotNumber()).orElse("Sorry, parking lot " + parkingLotId + " is full");
    }
    
}