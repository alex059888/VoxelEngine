package engine.handler;

import engine.gfx.Window;
import engine.gfx.shaders.Shader;
import engine.world.World;
import engine.world.worldTipes.ChunkManager;
import main.Game;

import static java.lang.Math.sqrt;

public class Handler {
    public static final float PI = 3.1415926535f, DR = 0.0174533f;
    public boolean showDir = true;
    public Shader defaultShader;

    public float renderDistance; //in chunks

    private int fps = 60;

    private float timeSinceLastTick = 0;

    private World world;
    private Game game;

    public Handler(Game game) {
        this.game = game;
        renderDistance = 2;
    }

    public Game getGame() {
        return game;
    }

    public Window getWindow() {
        return game.getWindow();
    }

    public int getWidth() {
        return game.getWindow().getWidth();
    }

    public int getHeight() {
        return game.getWindow().getHeight();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float dist(float ax, float ay, float bx, float by) {
        return (float) sqrt((bx-ax)*(bx-ax) + (by-ay)*(by-ay));
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public float getTimeSinceLastTick() {
        return timeSinceLastTick / 100000000;
    }

    public float getTimeSinceLastTickPerFps() {
        return timeSinceLastTick / 100000000 / fps;
    }

    public void setTimeSinceLastTick(float timeSinceLastTick) {
        this.timeSinceLastTick = timeSinceLastTick;
    }

    public Shader getDefaultShader() {
        return defaultShader;
    }

    public void setDefaultShader(Shader defaultShader) {
        this.defaultShader = defaultShader;
    }

    public ChunkManager getChunkManager() {
        return world.getChunkManager();
    }
}
