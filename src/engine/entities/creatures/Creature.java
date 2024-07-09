package engine.entities.creatures;

import engine.entities.Entity;
import engine.gfx.Camera;
import engine.gfx.textures.Texture;
import engine.handler.Handler;
import org.joml.Vector3f;

public class Creature extends Entity {
    protected boolean useCamera = false;

    protected float height = 0, camHeight = 0;

    public Vector3f cameraRotation;

    public Creature(float x, float y, float z, float rX, float rY, float rZ, Handler handler, int texID) {
        super(x, y, z, rX, rY, rZ, handler, texID);
        cameraRotation = new Vector3f(0,0,0);
    }

    public Creature(float x, float y, float z, Handler handler, int texID) {
        super(x, y, z, handler, texID);
        cameraRotation = new Vector3f(0,0,0);
    }

    @Override
    public void tick() {
        super.tick();
        mesh.tick();

        if (cameraRotation.x > 360f) cameraRotation.x -= 360f;
        if (cameraRotation.y > 360f) cameraRotation.y -= 360f;
        if (cameraRotation.z > 360f) cameraRotation.z -= 360f;
        if (cameraRotation.x < 0f) cameraRotation.x += 360f;
        if (cameraRotation.y < 0f) cameraRotation.y += 360f;
        if (cameraRotation.z < 0f) cameraRotation.z += 360f;
    }

    @Override
    public void render() {
        Texture.getTexture(texID).bind();
        handler.getDefaultShader().setTransform(transform.getTransformation());
        mesh.render();
    }

    public void rotateCamera(float x, float y, float z) {
        cameraRotation.x -= x * handler.getTimeSinceLastTickPerFps();
        cameraRotation.y -= y * handler.getTimeSinceLastTickPerFps();
        cameraRotation.z -= z * handler.getTimeSinceLastTickPerFps();

        if (cameraRotation.x >= 90 && cameraRotation.x <= 180) cameraRotation.x = 90;
        if (cameraRotation.x <= 270 && cameraRotation.x >= 180) cameraRotation.x = 270;
    }

    public boolean isUseCamera() {
        return useCamera;
    }

    public void setUseCamera(boolean useCamera) {
        this.useCamera = useCamera;
    }

    public Vector3f getCameraRotation() {
        return cameraRotation;
    }

    public void setCameraRotation(Vector3f cameraRotation) {
        this.cameraRotation = cameraRotation;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCamHeight() {
        return camHeight;
    }

    public void setCamHeight(float camHeight) {
        this.camHeight = camHeight;
    }
}
