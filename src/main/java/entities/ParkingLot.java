package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ParkingLot
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;

    @OneToMany(mappedBy = "parkingLot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingSlot> slots;


    // Default constructor required by JPA
    public ParkingLot() {}

    // Constructor with capacity
    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ParkingSlot> slots) {
        this.slots = slots;
    }

    // Additional methods and logic
}
