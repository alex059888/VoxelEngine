package engine.world.worldTipes;

import engine.entities.Block;
import engine.world.Chunk;
import engine.world.World;
public abstract class ChunkManager {

    protected float maxDX,minDX,maxDY,minDY,maxDZ,minDZ;

    protected World world;

    public ChunkManager(World world) {
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public abstract void setBlock(float x, float y, float z, String id);

    public abstract Block getBlock(float x, float y, float z);

    public abstract Chunk getChunk(float x, float y, float z);

    public abstract Chunk getChunk(String id);

    public abstract void render();

    public abstract void genWorld();

    public abstract void tick();

    public float getMaxDX() {
        return maxDX;
    }

    public void setMaxDX(float maxDX) {
        this.maxDX = maxDX;
    }

    public float getMinDX() {
        return minDX;
    }

    public void setMinDX(float minDX) {
        this.minDX = minDX;
    }

    public float getMaxDY() {
        return maxDY;
    }

    public void setMaxDY(float maxDY) {
        this.maxDY = maxDY;
    }

    public float getMinDY() {
        return minDY;
    }

    public void setMinDY(float minDY) {
        this.minDY = minDY;
    }

    public float getMaxDZ() {
        return maxDZ;
    }

    public void setMaxDZ(float maxDZ) {
        this.maxDZ = maxDZ;
    }

    public float getMinDZ() {
        return minDZ;
    }

    public void setMinDZ(float minDZ) {
        this.minDZ = minDZ;
    }
}
