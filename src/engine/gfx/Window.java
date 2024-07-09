package engine.gfx;

import engine.io.KeyListener;
import engine.io.MouseListener;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class Window {
    private long window;
    private int width, height;
    private String title;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 1);
        glfwWindowHint(GLFW_RESIZABLE, 1);
        glfwWindowHint(GLFW_DECORATED, 1);
        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::keyCallBack);

        if(window == NULL) {
            System.err.println("ERROR: Window not created!");
            glfwTerminate();
            assert false : "ERROR";
        }

        glfwSetFramebufferSizeCallback(window, this::framebufferSizeCallback);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void makeContextCurrent() {
        glfwMakeContextCurrent(window);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }

    private void framebufferSizeCallback(long window, int width, int height) {
        glViewport(0,0,width,height);
        this.width = width;
        this.height = height;
    }
}
