package waypoint.model;

/** The kinds of parking spot supported by the allocation system. */
public sealed interface SpotType permits SpotType.Compact, SpotType.Large, SpotType.Ev {
    record Compact() implements SpotType {}
    record Large() implements SpotType {}
    record Ev(int kw) implements SpotType {}

    /** Returns whether a vehicle size may use a spot type. */
    static boolean fits(VehicleSize size, SpotType spot) {
        if (size == null || spot == null) {
            return false;
        }

        return switch (size) {
            case MOTORCYCLE -> true;
            case COMPACT -> switch (spot) {
                case Compact compact -> true;
                case Large large -> true;
                case Ev ev -> false;
            };
            case LARGE -> switch (spot) {
                case Compact compact -> false;
                case Large large -> true;
                case Ev ev -> false;
            };
            case EV -> switch (spot) {
                case Compact compact -> false;
                case Large large -> false;
                case Ev ev -> ev.kw() > 0;
            };
        };
    }
}