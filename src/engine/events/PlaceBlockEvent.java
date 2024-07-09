package engine.events;

import engine.world.Chunk;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class PlaceBlockEvent implements Event{
    private Map<Vector3f, String> blockPlaceList;
    private EventManager eventManager;

    public PlaceBlockEvent(EventManager eventManager) {
        this.eventManager = eventManager;
        blockPlaceList = new HashMap<>();
    }

    @Override
    public void tick() {
        if (blockPlaceList.size() > 0) {
            for (Vector3f v:blockPlaceList.keySet()) {
                eventManager.getWorld().setBlock(v.x,v.y,v.z,blockPlaceList.get(v));
                eventManager.getWorld().getBlock(v.x-1, v.y, v.z).update();
                eventManager.getWorld().getBlock(v.x+1, v.y, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y-1, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y+1, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y, v.z-1).update();
                eventManager.getWorld().getBlock(v.x, v.y, v.z+1).update();

                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y+1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y+1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y-1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y-1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y+1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y+1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y-1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y-1,v.z-1).getId());

                eventManager.updateChunkMesh(Chunk.genId(
                        v.x-v.x%Chunk.SIZE,
                        v.y-v.y%Chunk.SIZE,
                        v.z-v.z%Chunk.SIZE
                ));
            }
            blockPlaceList.clear();
        }
    }

    public void placeBlock(Vector3f pos, String id) {
        if (!blockPlaceList.containsKey(pos)) {
            Vector3f v = new Vector3f(pos);
            if (pos.x%1 >= 0.5f) {
                v.x+=1;
            }
            if (pos.y%1 >= 0.5f) {
                v.y+=1;
            }
            if (pos.z%1 >= 0.5f) {
                v.z+=1;
            }
            if (eventManager.getWorld().getBlock(v.x,v.y,v.z).isAGas())
                blockPlaceList.put(v, id);
        }
    }
}
