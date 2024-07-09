package engine.entities;

import engine.gfx.Camera;
import engine.gfx.meshes.Mesh;
import engine.handler.Handler;
import engine.math.Ray;
import engine.math.Transform;
import engine.math.VectorMath;
import org.joml.Vector3f;

public abstract class Entity {
    protected Handler handler;
    protected Mesh mesh;
    protected final int texID;
    public Transform transform;

    public Entity(float x, float y, float z, float rX, float rY, float rZ, Handler handler, int texID) {
        transform = new Transform(new Vector3f(x,y,z), new Vector3f(rX, rY, rZ),1);
        this.handler = handler;
        this.texID = texID;
    }

    public Entity(float x, float y, float z, Handler handler, int texID) {
        transform = new Transform(new Vector3f(x,y,z), new Vector3f(0,0,0),1);
        this.handler = handler;
        this.texID = texID;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setScale(float scale) {
        transform.scale = scale;
    }

    public void tick() {
        if(transform.rotation.x < 0) transform.rotation.x = 2*Handler.PI;
        if(transform.rotation.x > 2*Handler.PI) transform.rotation.x = 0;
        if(transform.rotation.y < 0) transform.rotation.y = 2*Handler.PI;
        if(transform.rotation.y > 2*Handler.PI) transform.rotation.y = 0;
        if(transform.rotation.z < 0) transform.rotation.z = 2*Handler.PI;
        if(transform.rotation.z > 2*Handler.PI) transform.rotation.z = 0;
    }

    public abstract void render();

    public void setPosition(float x, float y, float z) {
        transform.position.x = x;
        transform.position.y = y;
        transform.position.z = z;
    }

    public void setRotation(float rX, float rY, float rZ) {
        transform.rotation.x = rX;
        transform.rotation.y = rY;
        transform.rotation.z = rZ;
    }

    public void move(float x, float y, float z) {
        transform.position.x += x * handler.getTimeSinceLastTickPerFps();
        transform.position.y += y * handler.getTimeSinceLastTickPerFps();
        transform.position.z += z * handler.getTimeSinceLastTickPerFps();
    }

    public void rotate(float x, float y, float z) {
        transform.rotation.x -= x * handler.getTimeSinceLastTickPerFps();
        transform.rotation.y -= y * handler.getTimeSinceLastTickPerFps();
        transform.rotation.z -= z * handler.getTimeSinceLastTickPerFps();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Handler getHandler() {
        return handler;
    }

    protected Vector3f castRay(Vector3f rayStart, Vector3f rayDir, float maxLength) {
        Ray ray = new Ray(new Vector3f(rayStart), new Vector3f(VectorMath.gradesToVec3(rayDir)));

        while (handler.getWorld().getBlock(ray.end.x, ray.end.y, ray.end.z).isAGas &&
                ray.length <= maxLength) {
            Vector3f currentBlock = VectorMath.blockCords(new Vector3f(ray.end));
            float dtX = (currentBlock.x * Block.BlockSize - ray.start.position.x) / ray.start.rotation.x;
            float dtY = (currentBlock.y * Block.BlockSize - ray.start.position.y) / ray.start.rotation.y;
            float dtZ = (currentBlock.z * Block.BlockSize - ray.start.position.z) / ray.start.rotation.z;

            System.out.println(ray.end.x + " " + ray.end.y + " " + ray.end.z + " ==== "
                    + Camera.get().position.x + " " + Camera.get().position.y + " " + Camera.get().position.z);

            ray.step(Math.min(dtX, Math.min(dtY, dtZ)));
        }
        System.out.println(ray.end.x + " " + ray.end.y + " " + ray.end.z + " " + ray.length);
        return ray.end;
    }
}
