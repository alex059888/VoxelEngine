package engine.gfx.meshes;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {
    private int vaoID, vboID, eboID;
    private float[] vertices;
    private int[] indices;
    private int posSize = 3, colorSize = 4, texSize = 2, floatSizeBytes = 4;
    private int vertexSizeBytes = (posSize + colorSize + texSize) * floatSizeBytes;

    public Mesh(Mesh mesh) {
        new Mesh(mesh.getVertices(), mesh.getIndices());
    }

    public Mesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer vboS = BufferUtils.createFloatBuffer(vertices.length);
        vboS.put(vertices);
        vboS.flip();
        glBufferData(GL_ARRAY_BUFFER, vboS, GL_STATIC_DRAW);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        IntBuffer eboS = BufferUtils.createIntBuffer(indices.length);
        eboS.put(indices);
        eboS.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, eboS, GL_STATIC_DRAW);

        glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, posSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, texSize, GL_FLOAT, false, vertexSizeBytes, (posSize + colorSize) * floatSizeBytes);
        glEnableVertexAttribArray(2);
    }

    public void tick() {
    }

    public void render() {
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
