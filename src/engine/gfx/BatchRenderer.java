package engine.gfx;

import engine.gfx.meshes.Mesh;
import engine.gfx.textures.Texture;
import engine.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class BatchRenderer {
    private List<Float> vboS;
    private List<Integer> eboS;
    private Mesh mesh;
    private Handler handler;

    private int faces;

    public BatchRenderer(Handler handler) {
        vboS = new ArrayList<>();
        eboS = new ArrayList<>();
        this.handler = handler;
        faces = 0;
    }

    public void update() {
        float[] v = new float[vboS.size()];
        int[] e = new int[eboS.size()];
        int i = 0;
        for (float f: vboS) {
            v[i] = f;
            i++;
        }
        i = 0;
        for (int f: eboS) {
            e[i] = f;
            i++;
        }
        mesh = new Mesh(v, e);
    }

    public void render() {
        Texture.getTexture(0).bind();
        mesh.render();
    }

    public void addEboS(int[] eboS) {
        for (int i : eboS) {
            this.eboS.add(i);
        }
        faces++;
    }

    public void addVboS(float[] vboS) {
        for (float f : vboS) {
            this.vboS.add(f);
        }
    }

    public void setVboS(List<Float> vboS) {
        this.vboS = vboS;
    }

    public void setEboS(List<Integer> eboS) {
        this.eboS = eboS;
    }

    public void clear() {
        vboS.clear();
        eboS.clear();
        faces = 0;
    }

    public int getFaces() {
        return faces;
    }
}
