package waypoint.model;

public final class NoSpotAvailable extends RuntimeException {
    public NoSpotAvailable() {
        super("No parking spot is available");
    }

    public NoSpotAvailable(String message) {
        super(message);
    }
}
