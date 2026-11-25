package waypoint.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Prefers the least-occupied level, then nearest within it. */
public final class LevelBalancingAllocator implements SpotAllocator {
    private final List<Spot> spots;

    public LevelBalancingAllocator(Collection<Spot> spots) {
        this.spots = new ArrayList<>(Objects.requireNonNull(spots, "spots"));
    }

    public LevelBalancingAllocator(Spot... spots) {
        this(List.of(spots));
    }

    @Override
    public synchronized Spot allocate(Vehicle vehicle) throws NoSpotAvailable {
        Objects.requireNonNull(vehicle, "vehicle");
        Spot selected = spots.stream()
                .filter(Spot::isFree)
                .filter(spot -> SpotType.fits(vehicle.size(), spot.type()))
                .min(Comparator
                        .comparingInt((Spot spot) -> occupiedCount(spot.level()))
                        .thenComparingInt(Spot::distanceFromGate)
                        .thenComparingInt(Spot::level))
                .orElseThrow(NoSpotAvailable::new);
        selected.occupy();
        return selected;
    }

    private int occupiedCount(int level) {
        int count = 0;
        for (Spot spot : spots) {
            if (spot.level() == level && !spot.isFree()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public synchronized void release(Spot spot) {
        if (spot != null && spots.contains(spot)) {
            spot.release();
        }
    }
}