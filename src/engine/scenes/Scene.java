package engine.scenes;

import engine.gfx.Camera;
import engine.handler.Handler;
import org.joml.Vector3f;

public abstract class Scene {
    protected Handler handler;
    protected Camera camera;

    public Scene(Handler handler) {
        this.handler = handler;
        camera = new Camera(new Vector3f(), new Vector3f(), handler);
    }

    public abstract void tick();

    public abstract void render();
}
