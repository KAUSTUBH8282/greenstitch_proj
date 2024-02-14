package entities;

import jakarta.persistence.*;

@Entity
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotNumber;

    @Column(nullable = false)
    private boolean isOccupied;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Car parkedCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id") // This column will be added to the ParkingSlot table.
    private ParkingLot parkingLot;

    // Constructor
    public ParkingSlot() {
        // Default constructor
    }

    public ParkingSlot(int slotNumber, boolean isOccupied, Car parkedCar) {
        this.slotNumber = slotNumber;
        this.isOccupied = isOccupied;
        this.parkedCar = parkedCar;
    }

    // Getter for slotNumber
    public int getSlotNumber() {
        return slotNumber;
    }

    // Setter for slotNumber
    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    // Getter for isOccupied
    public boolean getIsOccupied() {
        return isOccupied;
    }

    // Setter for isOccupied
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    // Getter for parkedCar
    public Car getParkedCar() {
        return parkedCar;
    }

    // Setter for parkedCar
    public void setParkedCar(Car parkedCar) {
        this.parkedCar = parkedCar;
    }
    
    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) 
    {
        this.parkingLot = parkingLot;
    }
}

