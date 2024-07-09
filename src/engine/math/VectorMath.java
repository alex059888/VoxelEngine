package engine.math;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class VectorMath {

    public static Vector3f gradesToVec3(float rX, float rY, float rZ) {
        /*return new Vector3f(
                (float) (Math.sin(Math.toRadians(rY)) + Math.cos(Math.toRadians(rZ)))/2,
                (float) (Math.sin(Math.toRadians(rZ)) + Math.cos(Math.toRadians(rX)))/2,
                (float) (Math.sin(Math.toRadians(rX)) + Math.cos(Math.toRadians(rY)))/2
        );*/
        return new Vector3f(
                (float) Math.sin(Math.toRadians(rY)),
                (float) Math.sin(Math.toRadians(rX)),
                -(float)Math.cos(Math.toRadians(rY))
        );
    }

    public static Vector3f gradesToVec3(Vector3f rot) {
        return gradesToVec3(rot.x, rot.y, rot.z);
    }

    public static Vector3f blockCords(Vector3f position) {
        Vector3f v = new Vector3f(position);
        if (position.x%1 >= 0.5f) {
            v.x+=1;
        }
        if (position.y%1 >= 0.5f) {
            v.y+=1;
        }
        if (position.z%1 >= 0.5f) {
            v.z+=1;
        }
        v.x = (float) Math.floor(v.x);
        v.y = (float) Math.floor(v.y);
        v.z = (float) Math.floor(v.z);
        return v;
    }

    public static float dist2p(Vector3f p1, Vector3f p2) {
        return Math.sqrt(pow2(p2.x - p1.x) + pow2(p2.y - p1.y) + pow2(p2.z - p1.z));
    }

    public static float dist2p(Vector2f p1, Vector2f p2) {
        return Math.sqrt(pow2(p2.x-p1.x) + pow2(p2.y - p1.y));
    }

    public static float pow2(float f) {
        return f*f;
    }
}
