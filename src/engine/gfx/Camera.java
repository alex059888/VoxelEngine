package engine.gfx;

import engine.entities.creatures.Creature;
import engine.handler.Handler;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projection, view;

    public float sensitivity;

    public float FOV = 90.0f, FAR_PLANE = 10000f, NEAR_PLANE = 0.1f;

    private Handler handler;

    public Vector3f position, rotation;

    private static Camera camera;

    public static Camera get() {
        if (camera == null)
            new Camera(new Vector3f(), new Vector3f(), null);
        return camera;
    }

    public Camera(Vector3f position, Vector3f rotation, Handler handler) {
        this.position = position;
        this.rotation = rotation;
        projection = new Matrix4f();
        view = new Matrix4f();
        this.handler = handler;
        camera = this;
        sensitivity = 120f;
        adjustProjection();
    }

    public Camera(Creature creature) {
        new Camera(creature.transform.position, creature.cameraRotation, creature.getHandler());
    }

    public void adjustProjection() {
        projection.identity();
        float aspect = (float) handler.getWidth() / (float) handler.getHeight();
        projection = new Matrix4f().perspective((float) Math.toRadians(FOV),aspect,NEAR_PLANE,FAR_PLANE);
    }

    public Matrix4f getView() {
        view.identity();
        view = createViewMatrix();
        return view;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    public Matrix4f createViewMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
        matrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        matrix.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
        matrix.translate(new Vector3f(-position.x, -position.y, -position.z));

        return matrix;
    }
}
