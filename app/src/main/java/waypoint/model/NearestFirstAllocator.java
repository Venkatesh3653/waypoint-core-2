package waypoint.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Lowest distanceFromGate wins. */
public final class NearestFirstAllocator implements SpotAllocator {
    private final List<Spot> spots;

    public NearestFirstAllocator(Collection<Spot> spots) {
        this.spots = new ArrayList<>(Objects.requireNonNull(spots, "spots"));
    }

    public NearestFirstAllocator(Spot... spots) {
        this(List.of(spots));
    }

    @Override
    public synchronized Spot allocate(Vehicle vehicle) throws NoSpotAvailable {
        Objects.requireNonNull(vehicle, "vehicle");
        Spot selected = spots.stream()
                .filter(Spot::isFree)
                .filter(spot -> SpotType.fits(vehicle.size(), spot.type()))
                .min(Comparator.comparingInt(Spot::distanceFromGate))
                .orElseThrow(NoSpotAvailable::new);
        selected.occupy();
        return selected;
    }

    @Override
    public synchronized void release(Spot spot) {
        if (spot != null && spots.contains(spot)) {
            spot.release();
        }
    }
}