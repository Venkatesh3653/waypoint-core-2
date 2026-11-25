package waypoint.model;

import java.util.Objects;

public final class Spot {
    private final SpotType type;
    private final int level;
    private final int distanceFromGate;
    private boolean free;

    public Spot(SpotType type, int level, int distanceFromGate) {
        this.type = Objects.requireNonNull(type, "type");
        this.level = level;
        this.distanceFromGate = distanceFromGate;
        this.free = true;
    }

    public SpotType type() {
        return type;
    }

    public SpotType getType() {
        return type;
    }

    public SpotType spotType() {
        return type;
    }

    public int level() {
        return level;
    }

    public int getLevel() {
        return level;
    }

    public int distanceFromGate() {
        return distanceFromGate;
    }

    public int getDistanceFromGate() {
        return distanceFromGate;
    }

    public synchronized boolean isFree() {
        return free;
    }

    public synchronized void occupy() {
        free = false;
    }

    public synchronized void release() {
        free = true;
    }

    @Override
    public String toString() {
        return "Spot[" + type + ", level=" + level
                + ", distance=" + distanceFromGate + ", free=" + free + "]";
    }
}
