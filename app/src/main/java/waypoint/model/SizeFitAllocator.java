package waypoint.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Smallest spot that fits. A compact car may take a large bay
 *  only when no compact bay remains. */
public final class SizeFitAllocator implements SpotAllocator {
    private final List<Spot> spots;

    public SizeFitAllocator(Collection<Spot> spots) {
        this.spots = new ArrayList<>(Objects.requireNonNull(spots, "spots"));
    }

    public SizeFitAllocator(Spot... spots) {
        this(List.of(spots));
    }

    @Override
    public synchronized Spot allocate(Vehicle vehicle) throws NoSpotAvailable {
        Objects.requireNonNull(vehicle, "vehicle");
        Spot selected = spots.stream()
                .filter(Spot::isFree)
                .filter(spot -> SpotType.fits(vehicle.size(), spot.type()))
                .min(Comparator
                        .comparingInt((Spot spot) -> rank(vehicle.size(), spot.type()))
                        .thenComparingInt(Spot::distanceFromGate)
                        .thenComparingInt(Spot::level))
                .orElseThrow(NoSpotAvailable::new);
        selected.occupy();
        return selected;
    }

    private int rank(VehicleSize size, SpotType type) {
        if (type instanceof SpotType.Compact) {
            return 0;
        }
        if (type instanceof SpotType.Ev) {
            return size == VehicleSize.EV ? 0 : 2;
        }
        return 1;
    }

    @Override
    public synchronized void release(Spot spot) {
        if (spot != null && spots.contains(spot)) {
            spot.release();
        }
    }
}