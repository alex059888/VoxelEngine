package engine.world.worldTipes.InfiniteWorld;

import engine.entities.Block;
import engine.gfx.Camera;
import engine.handler.Handler;
import engine.world.Chunk;
import engine.world.World;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class IWChunkManager implements Runnable{
    private static List<Chunk> chunks, visible;
    private List<String> expectedLoadList, loadList;

    private boolean shouldClose = false;

    private World world;

    private Handler handler;

    private Thread thread;

    private static IWChunkManager chunkManager;

    public IWChunkManager(Handler handler, World world) {
        this.world = world;
        this.handler = handler;

        chunkManager = this;

        init();

        thread = new Thread(this);

        thread.start();
    }

    public void run() {
        while (!shouldClose) {
            setListToLoadChunks();
            verifyLoadList();
            removeToFarrChunks();
            loadChunks();
            setVisibleChunks();
        }

        unloadChunks();

        stop();
    }

    public void saveChunks() {
        for (Chunk c: chunks) {
            c.save();
        }
    }

    public void saveChunk(Chunk chunk) {
        chunk.save();
    }

    public void unloadChunk(Chunk chunk) {
        saveChunk(chunk);

        chunks.remove(chunk);
        for (Chunk c: visible) {
            if (c == chunk) {
                visible.remove(c);
                continue;
            }
        }
    }

    public void unloadChunks() {
        saveChunks();
        chunks.clear();
        visible.clear();

        chunks = null;
        visible = null;
    }

    public void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            assert false: "ChunkManager error";
        }
    }

    public void init() {
        chunks = new ArrayList<>();
        expectedLoadList = new ArrayList<>();
        visible = new ArrayList<>();
        loadList = new ArrayList<>();

        setListToLoadChunks();
        loadChunks();
        setVisibleChunks();

        //chunks.add(new Chunk("","0/0/0",new Transform(new Vector3f(0,0,0),new Vector3f(0,0,0),1),handler, this));
    }

    private void setListToLoadChunks() {
        for (int x = (int) Camera.get().getPosition().x - (int) handler.renderDistance;
             x <= (int) Camera.get().getPosition().x + (int) handler.renderDistance; x++) {
            for (int y = (int) Camera.get().getPosition().y - (int) handler.renderDistance;
                 y <= (int) Camera.get().getPosition().y + (int) handler.renderDistance; y++) {
                for (int z = (int) Camera.get().getPosition().z - (int) handler.renderDistance;
                     z <= (int) Camera.get().getPosition().z + (int) handler.renderDistance; z++) {
                    expectedLoadList.add(Chunk.genId(new Vector3f(x * Chunk.SIZE, y * Chunk.SIZE, z * Chunk.SIZE)));
                }
            }
        }
    }

    private void verifyLoadList() {
        for (String s: expectedLoadList) {
            boolean used = false;
            for (Chunk c: chunks) {
                if (c.getId().equals(s))
                    used = true;
            }
            if (!used)
                loadList.add(new String(s));
        }
        expectedLoadList.clear();
    }

    private void removeToFarrChunks() {
        /*
        if (chunks.size() > 0) {
            boolean unload;
            for (Chunk chunk : chunks) {
                unload = true;
                for (String id : load) {
                    if (chunk.getId().equals(id)) {
                        unload = false;
                        load.remove(id);
                    }
                }
                if (unload) {
                    if (chunk.getTransform().position.x < Camera.get().position.x) {
                        getChunk(chunk.getTransform().position.x + Chunk.CHUNK_SIZE, chunk.getTransform().position.y,
                                chunk.getTransform().position.z).updateBatchRenderer();
                    }
                    if (chunk.getTransform().position.x > Camera.get().position.x) {
                        getChunk(chunk.getTransform().position.x - Chunk.CHUNK_SIZE, chunk.getTransform().position.y,
                                chunk.getTransform().position.z).updateBatchRenderer();
                    }
                    if (chunk.getTransform().position.y < Camera.get().position.y) {
                        getChunk(chunk.getTransform().position.x, chunk.getTransform().position.y + Chunk.CHUNK_SIZE,
                                chunk.getTransform().position.z).updateBatchRenderer();
                    }
                    if (chunk.getTransform().position.y > Camera.get().position.y) {
                        getChunk(chunk.getTransform().position.x, chunk.getTransform().position.y - Chunk.CHUNK_SIZE,
                                chunk.getTransform().position.z).updateBatchRenderer();
                    }
                    if (chunk.getTransform().position.z < Camera.get().position.z) {
                        getChunk(chunk.getTransform().position.x, chunk.getTransform().position.y,
                                chunk.getTransform().position.z + Chunk.CHUNK_SIZE).updateBatchRenderer();
                    }
                    if (chunk.getTransform().position.z > Camera.get().position.z) {
                        getChunk(chunk.getTransform().position.x, chunk.getTransform().position.y,
                                chunk.getTransform().position.z - Chunk.CHUNK_SIZE).updateBatchRenderer();
                    }
                    chunks.remove(chunk);
                }
            }
        }
         */
    }

    private void loadChunks() {
        //for (String c : loadList)
            //chunks.add(new Chunk("", c, new Transform(Chunk.idToVector3f(c), new Vector3f(0, 0, 0), 1), handler, world));
        loadList.clear();
    }

    private void setVisibleChunks() {
        visible.clear();
        for (Chunk chunk : chunks) {
            if (chunk.getBatchRenderer().getFaces() > 0)
                visible.add(chunk);
        }
    }

    public Chunk getChunk(Vector3f pos) {
        return getChunk(pos.x, pos.y, pos.z);
    }

    public Chunk getChunk(float x, float y, float z) {
        return getChunk(Chunk.genId(new Vector3f(x - x % Chunk.SIZE, y - y % Chunk.SIZE, z - z % Chunk.SIZE)));
    }

    public Chunk getChunk(String id) {
        for (Chunk c : chunks) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public static IWChunkManager get() {
        return chunkManager;
    }

    public Block getBlock(float x, float y, float z) {
        return getBlock((int) x, (int) y, (int) z);
    }

    public Block getBlock(int x, int y, int z) {
        if (getChunk(Chunk.genId(new Vector3f((float) x - x % Chunk.SIZE,
                (float) y - y % Chunk.SIZE, (float) z - z % Chunk.SIZE))) != null)
            return getChunk(Chunk.genId(new Vector3f((float) x - x % Chunk.SIZE,
                    (float) y - y % Chunk.SIZE, (float) z - z % Chunk.SIZE))).getBlock(
                    x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE);
        return null;
    }

    public void setBlock(float x, float y, float z, String id) {
        setBlock((int) x, (int) y, (int) z, id);
    }

    public void setBlock(int x, int y, int z, String id) {
        if (getChunk(Chunk.genId(new Vector3f((float) x - x % Chunk.SIZE,
                (float) y - y % Chunk.SIZE, (float) z - z % Chunk.SIZE))) != null)
            getChunk(Chunk.genId(new Vector3f((float) x - x % Chunk.SIZE,
                    (float) y - y % Chunk.SIZE, (float) z - z % Chunk.SIZE))).setBlock(
                    x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE, id);
        //cod pt a pune in lista de setat mai tarziu
    }

    public static synchronized void render() {
        for (Chunk c : visible) {
            c.renderSolids();
        }
        for (Chunk c : visible) {
            c.renderLiquids();
        }
    }

    public void close() {
        shouldClose = true;
    }
}
