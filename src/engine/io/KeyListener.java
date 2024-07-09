package engine.io;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private static KeyListener keyListener;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {
    }

    public static KeyListener get() {
        if (keyListener == null)
            keyListener = new KeyListener();
        return keyListener;
    }

    public static void keyCallBack(long window, int key, int scanCode, int action, int mods) {
        if (key >= 0)
            if (action == GLFW_PRESS) {
                get().keyPressed[key] = true;
            } else if (action == GLFW_RELEASE) {
                get().keyPressed[key] = false;
            }
    }

    public static boolean isKeyPressed(int key) {
        return get().keyPressed[key];
    }
}
