package engine.events;

import engine.world.World;
import org.joml.Vector3f;

public class EventManager {
    private World world;
    private ChunkMeshUpdateEvent chunkMeshUpdateEvent;
    private BlockBreakEvent blockBreakEvent;
    private PlaceBlockEvent placeBlockEvent;

    public EventManager(World world) {
        this.world = world;
        chunkMeshUpdateEvent = new ChunkMeshUpdateEvent(this);
        blockBreakEvent = new BlockBreakEvent(this);
        placeBlockEvent = new PlaceBlockEvent(this);
    }

    public void tick() {
        chunkMeshUpdateEvent.tick();
        blockBreakEvent.tick();
        placeBlockEvent.tick();
    }

    public World getWorld() {
        return world;
    }

    public void updateChunkMesh(String id) {
        if (id != null)
        chunkMeshUpdateEvent.updateChunkMesh(id);
    }

    public void breakBlock(Vector3f pos) {
        blockBreakEvent.breakBlock(pos);
    }

    public void placeBlock(Vector3f pos, String id) {
        placeBlockEvent.placeBlock(pos, id);
    }
}
