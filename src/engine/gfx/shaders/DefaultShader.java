package engine.gfx.shaders;

import org.joml.Matrix4f;

public class DefaultShader extends Shader{
    private final String frag = "DefaultFrag.glsl", vert = "DefaultVert.glsl";

    public DefaultShader() {
        super();

        addVertexShader(loadShader(vert));
        addFragmentShader(loadShader(frag));
        compileShader();
    }

    @Override
    public void bindAttributes() {
        addUniform("tex");
        addUniform("projection");
        addUniform("view");
        addUniform("transform");
    }

    @Override
    public void setProjection(Matrix4f projection) {
        setUniforms("projection", projection);
    }

    @Override
    public void setView(Matrix4f view) {
        setUniforms("view", view);
    }

    @Override
    public void setTransform(Matrix4f transform) {
        setUniforms("transform", transform);
    }

    @Override
    public void setTime(int time) {
        setUniforms("time", time);
    }
}