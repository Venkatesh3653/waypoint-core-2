package waypoint.model;

import java.util.Objects;

public final class Vehicle {
    private final VehicleSize size;

    public Vehicle(VehicleSize size) {
        this.size = Objects.requireNonNull(size, "size");
    }

    public VehicleSize size() {
        return size;
    }

    public VehicleSize getSize() {
        return size;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Vehicle vehicle && size == vehicle.size;
    }

    @Override
    public int hashCode() {
        return size.hashCode();
    }

    @Override
    public String toString() {
        return "Vehicle[" + size + "]";
    }
}
