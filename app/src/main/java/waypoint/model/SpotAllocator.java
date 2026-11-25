package waypoint.model;

/**
 * Allocates and releases parking spots.
 *
 * <p><strong>Preconditions:</strong> {@code allocate} requires a non-null vehicle.
 * The allocator must contain at least one configured spot. {@code release} accepts
 * a spot allocated from this allocator or {@code null}; neither action requires
 * any pre-existing free state.</p>
 *
 * <p><strong>Postconditions:</strong> A successful {@code allocate} returns a
 * non-null, occupied spot that is compatible with the vehicle and that was free
 * before allocation. If no compatible free spot exists, it throws
 * {@link NoSpotAvailable} and leaves allocator state unchanged. {@code release}
 * leaves the released spot free.</p>
 *
 * <p><strong>Invariants:</strong> A spot is never allocated twice while occupied;
 * configured spots remain distinct; and free/occupied state is the sole mutable
 * allocation state.</p>
 *
 * <p><strong>Timing:</strong> Allocation and release complete with no background
 * work or timing-dependent behaviour.</p>
 *
 * <p><strong>Side effects:</strong> Allocation changes exactly one chosen spot
 * from free to occupied. Release changes a configured spot to free; it does not
 * modify other spots. A null release is a no-op.</p>
 *
 * <p><strong>Idempotency:</strong> Releasing the same spot repeatedly is harmless.
 * Releasing foreign or unknown spots has no effect.</p>
 *
 * <p><strong>Threading:</strong> Implementations serialize allocation and release
 * so concurrent callers observe a consistent spot state.</p>
 *
 * <p><strong>Ordering:</strong> A strategy may impose a deterministic preference
 * among candidates, but allocation must never reuse an occupied spot.</p>
 */
public interface SpotAllocator {
    Spot allocate(Vehicle vehicle) throws NoSpotAvailable;
    void release(Spot spot);
}