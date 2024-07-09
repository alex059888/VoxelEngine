package engine.world;

import engine.entities.Block;
import engine.entities.EntityManager;
import engine.entities.creatures.Creature;
import engine.entities.creatures.Player;
import engine.events.EventManager;
import engine.handler.Handler;
import engine.world.worldTipes.ChunkManager;

public abstract class World {
    protected Handler handler;

    protected EntityManager entityManager;
    protected int width,height;
    protected Creature player;

    protected EventManager eventManager;

    protected ChunkManager chunkManager;

    protected boolean closingWorld = false;

    protected String worldPath;

    public World(Handler handler, String worldName, ChunkManager chunkManager) {
        this.handler = handler;
        this.chunkManager = chunkManager;
        this.chunkManager.setWorld(this);

        chunkManager.genWorld();

        entityManager = new EntityManager(handler);
        eventManager = new EventManager(this);

        loadWorld(worldName);
    }

    private void loadWorld(String worldName) {
        worldPath = "./res/worlds/" + worldName;

        /*try {
            Scanner scanner = new Scanner(new FileReader(worldPath));
            width = scanner.nextInt();
            height = scanner.nextInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            assert false : "Error: World couldn't load!";
        }*/
        player = new Player(128,64,128,handler,0);
        entityManager.addEntity(player);
    }

    public void render() {
        entityManager.render();
        chunkManager.render();
    }

    public void tick() {
        entityManager.tick();
        chunkManager.tick();
        eventManager.tick();
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean isClosingWorld() {
        return closingWorld;
    }

    public String getWorldPath() {
        return worldPath;
    }

    public void setClosingWorld(boolean closingWorld) {
        this.closingWorld = closingWorld;
    }

    public abstract void setBlock(float x, float y, float z, String id);

    public abstract Block getBlock(float x, float y, float z);

    public abstract Chunk getChunk(String id);

    public abstract Chunk getChunk(float x, float y, float z);

    public void setChunkManager(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    public Creature getPlayer() {
        return player;
    }

    public void setPlayer(Creature player) {
        this.player = player;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
