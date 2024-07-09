package engine.events;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshUpdateEvent implements Event{

    private List<String> ids;
    private EventManager eventManager;

    public ChunkMeshUpdateEvent(EventManager eventManager) {
        ids = new ArrayList<>();
        this.eventManager = eventManager;
    }

    @Override
    public void tick() {
        if (ids.size()>0) {
            for (String i : ids) {
                if (!(eventManager.getWorld().getChunk(i) == null))
                    eventManager.getWorld().getChunk(i).updateBatchRenderer();
            }
            ids.clear();
        }
    }

    public void updateChunkMesh(String id) {
        if (!ids.contains(id))
            ids.add(id);
    }
}
