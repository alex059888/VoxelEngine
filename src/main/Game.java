package main;

import engine.gfx.Window;
import engine.handler.Handler;
import engine.io.MouseListener;
import engine.scenes.LevelScene;
import engine.scenes.Scene;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class Game {
    private int width, height;
    private String title;
    private Window window;
    private float r = 0f, g = 0.5f, b = 0.5f, a = 1;
    private Scene scene;
    private Handler handler;

    public static int time = 0;

    public Game(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

        handler = new Handler(this);
    }

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window.getWindow());
        glfwDestroyWindow(window.getWindow());
        glfwTerminate();
    }

    private void init() {
        glfwInit();

        window = new Window(width, height, title);
        if(window == null)
            throw new IllegalStateException("Window could not be created.");

        window.makeContextCurrent();

        glfwSwapInterval(1);

        GL.createCapabilities();

        glViewport(0,0,width,height);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glCullFace(GL_FRONT);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        scene = new LevelScene(handler,"Tutorial.map");
    }

    private void loop() {
        double timePerTick = 1000000000 / handler.getFps();
        double dt = 0;
        long now, lastTime = System.nanoTime();
        long lastTickTime = System.nanoTime();

        while (!window.shouldClose()) {
            now = System.nanoTime();
            dt += (now - lastTime) / timePerTick;
            lastTime = now;

            if(dt >= 1) {
                dt--;
                long currentTickTime = System.nanoTime();
                handler.setTimeSinceLastTick(currentTickTime - lastTickTime);
                lastTickTime = currentTickTime;

                glfwPollEvents();

                glClearColor(r, g, b, a);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                tick();
                render();

                glfwSwapBuffers(window.getWindow());
            }
        }
    }

    private void tick() {
        scene.tick();
        MouseListener.endFrame();
        time++;
        if(time >= 3600) time -= 3600;
    }

    private void render() {
        scene.render();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Window getWindow() {
        return window;
    }
}
