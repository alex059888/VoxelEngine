package engine.world.worldTipes.LimitedWorld;

import engine.entities.Block;
import engine.handler.Handler;
import engine.world.Chunk;
import engine.world.World;

public class SmallWorld extends World {

    public SmallWorld(Handler handler, int X, int Y, int Z) {
        super(handler, "SmallWorld", new LWChunkManager(X,Y,Z,handler,null,null));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void setBlock(float x, float y, float z, String id) {
        chunkManager.setBlock(x,y,z,id);
    }

    @Override
    public Block getBlock(float x, float y, float z) {
        return chunkManager.getBlock(x,y,z);
    }

    @Override
    public Chunk getChunk(String id) {
        return chunkManager.getChunk(id);
    }

    @Override
    public Chunk getChunk(float x, float y, float z) {
        return chunkManager.getChunk(x,y,z);
    }

    @Override
    public void render() {
        super.render();
    }
}
