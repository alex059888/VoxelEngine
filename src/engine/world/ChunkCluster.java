package engine.world;

import engine.entities.Block;
import engine.handler.Handler;
import engine.math.Transform;
import engine.world.worldTipes.ChunkManager;
import org.joml.Vector3f;

public class ChunkCluster {
    private Chunk[][][] chunks;
    private Handler handler;
    private Transform transform;
    private ChunkManager chunkManager;

    public static final int SIZE = Chunk.SIZE * 2;

    public ChunkCluster(Transform transform, ChunkManager chunkManager, Handler handler) {
        this.handler = handler;
        this.transform = transform;
        this.chunkManager = chunkManager;

        chunks = new Chunk[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    chunks[i][j][k] = new Chunk(
                            Chunk.genId(transform.position.x + Chunk.SIZE * i, transform.position.y + Chunk.SIZE * j,
                                    transform.position.z + Chunk.SIZE * k),
                            new Transform(new Vector3f(
                                    transform.position.x + Chunk.SIZE * i, transform.position.y + Chunk.SIZE * j,
                                    transform.position.z + Chunk.SIZE * k),
                                    new Vector3f(transform.rotation),
                                    transform.scale), handler, chunkManager);
                }
            }
        }
    }

    public Block getBlock(float x, float y, float z) {
        return getBlock((int) x, (int) y, (int) z);
    }

    public Block getBlock(int x, int y, int z) {
        return getChunk(x,y,z)
                .getBlock(x%Chunk.SIZE,y%Chunk.SIZE,z%Chunk.SIZE);
    }

    public void setBlock(int x, int y, int z, String id) {
        getChunk(x,y,z).setBlock(x%Chunk.SIZE,y%Chunk.SIZE,z%Chunk.SIZE,id);
    }

    public Chunk getChunk(float x, float y, float z) {
        return getChunk((int) x, (int) y, (int) z);
    }

    public Chunk getChunk(int x, int y, int z) {
        return chunks[x/Chunk.SIZE %2][y/Chunk.SIZE %2][z/Chunk.SIZE %2];
    }

    public void updateBatchRenderers() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    chunks[i][j][k].updateBatchRenderer();
    }

    public void render() {
    }

    public void renderSolids() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    chunks[i][j][k].renderSolids();
    }

    public void renderLiquids() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    chunks[i][j][k].renderLiquids();
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
