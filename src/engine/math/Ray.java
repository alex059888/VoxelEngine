package engine.math;

import org.joml.Vector3f;

public class Ray {
    public final Transform start;
    public Vector3f end;
    public float length;

    public Ray(Vector3f position, Vector3f rotation) {
        start = new Transform(position, rotation, 1);
        end = new Vector3f(start.position);
        start.scale = 1;
        length = 0;
    }

    public void step(float f) {
        end = end.add(start.rotation.mul(f));
        length = VectorMath.dist2p(start.position, end);
    }

    public void setLength(float length) {
        this.length = length;
        end = start.position.add(start.rotation.mul(length));
    }
}
