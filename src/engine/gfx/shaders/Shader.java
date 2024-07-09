package engine.gfx.shaders;

import engine.gfx.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {
    private HashMap<String, Integer> uniforms;

    private int program;

    public Shader() {
        program = glCreateProgram();
        uniforms = new HashMap<>();

        if (program == 0) {
            System.err.println("Shader creation failed! No valid memory!");
            System.exit(1);
        }
    }

    public abstract void bindAttributes();

    protected void bindAttributes(int attribute, String varName) {
        glBindAttribLocation(program, attribute, varName);
    }

    public void bind() {
        glUseProgram(program);
    }

    public static void unbind() {
        glUseProgram(0);
    }

    protected void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Err: Could not find uniform: " + uniform);
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);
    }

    protected void setUniforms(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    protected void setUniforms(String uniformName, Vector2f value) {
        glUniform2f(uniforms.get(uniformName), value.x, value.y);
    }

    protected void setUniforms(String uniformName, int texture) {
        glUniform1i(uniforms.get(uniformName), texture);
    }

    protected void setUniforms(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    protected void setUniforms(String uniformName, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buffer.put(value.get(i, j));
            }
        }

        buffer.flip();
        glUniformMatrix4fv(uniforms.get(uniformName), false, buffer);
    }

    protected void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    protected void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    protected void compileShader() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println(glGetProgramInfoLog(1024));
            System.exit(1);
        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println(glGetProgramInfoLog(1024));
            System.exit(1);
        }

        bindAttributes();
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed! No valid memory!");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    public static String loadShader(String fileName) {
        StringBuilder source = new StringBuilder();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line);
                source.append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return source.toString();
    }

    public void setTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(program, varName);
        glUniform1i(varLocation, slot);
    }

    public abstract void setTransform(Matrix4f transform);

    public abstract void setProjection(Matrix4f projection);

    public abstract void setView(Matrix4f view);

    public abstract void setTime(int time);
}