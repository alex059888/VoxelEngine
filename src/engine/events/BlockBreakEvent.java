package engine.events;

import engine.world.Chunk;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakEvent implements Event{
    private List<Vector3f> blockCords;
    private EventManager eventManager;

    public BlockBreakEvent(EventManager eventManager) {
        this.eventManager = eventManager;
        blockCords = new ArrayList<>();
    }

    @Override
    public void tick() {
        if (blockCords.size() > 0) {
            for (Vector3f v : blockCords) {
                eventManager.getWorld().setBlock(v.x, v.y, v.z, "0");
                eventManager.getWorld().getBlock(v.x - 1, v.y, v.z).update();
                eventManager.getWorld().getBlock(v.x + 1, v.y, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y - 1, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y + 1, v.z).update();
                eventManager.getWorld().getBlock(v.x, v.y, v.z - 1).update();
                eventManager.getWorld().getBlock(v.x, v.y, v.z + 1).update();

                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y+1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y+1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y-1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x+1,v.y-1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y+1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y+1,v.z-1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y-1,v.z+1).getId());
                eventManager.updateChunkMesh(eventManager.getWorld().getChunkManager().getChunk(v.x-1,v.y-1,v.z-1).getId());

                eventManager.updateChunkMesh(Chunk.genId(v));
            }
            blockCords.clear();
        }
    }

    public void breakBlock(Vector3f pos) {
        if (!blockCords.contains(pos)) {
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
            blockCords.add(v);
        }
    }
}
