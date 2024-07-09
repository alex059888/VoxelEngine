package engine.entities.creatures;

import engine.entities.Block;
import engine.gfx.Camera;
import engine.gfx.meshes.Mesh;
import engine.gfx.textures.TexSizes;
import engine.gfx.textures.Texture;
import engine.handler.Handler;
import engine.io.KeyListener;
import engine.io.MouseListener;
import org.joml.Vector3f;

import static engine.gfx.textures.Texture.getTexCoord;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends Creature {
    private float DEFAULT_SPEED = 30f; // 3m/s

    private float[] vboS;

    private String selectedBlock = "2";

    private final int[] eboS = {
            0, 2, 1, 1, 2, 3
    };

    public Player(float x, float y, float z, float rX, float rY, float rZ, Handler handler, int texID) {
        super(x, y, z, rX, rY, rZ, handler, texID);
        height = 1.8f;
        camHeight = height-0.2f;
        genMesh();
    }

    public Player(float x, float y, float z, Handler handler, int texID) {
        super(x, y, z, handler, texID);
        height = 1.8f;
        camHeight = height-0.2f;
        genMesh();
    }

    private void genMesh() {
        vboS = new float[]{
                -0.5f, 0, -0.5f, 1, 1, 1, 1,
                getTexCoord(0, Texture.getTexture(texID).getTexSize(), TexSizes.X16),
                getTexCoord(1, Texture.getTexture(texID).getTexSize(), TexSizes.X16), //DL
                -0.5f, 2, -0.5f, 1, 1, 1, 1,
                getTexCoord(0, Texture.getTexture(texID).getTexSize(), TexSizes.X16),
                getTexCoord(0, Texture.getTexture(texID).getTexSize(), TexSizes.X16), //UL
                0.5f, 0, -0.5f, 1, 1, 1, 1,
                getTexCoord(1, Texture.getTexture(texID).getTexSize(), TexSizes.X16),
                getTexCoord(1, Texture.getTexture(texID).getTexSize(), TexSizes.X16), //DR
                0.5f, 2, -0.5f, 1, 1, 1, 1,
                getTexCoord(1, Texture.getTexture(texID).getTexSize(), TexSizes.X16),
                getTexCoord(0, Texture.getTexture(texID).getTexSize(), TexSizes.X16)  //UR
        };
        mesh = new Mesh(vboS, eboS);
    }

    @Override
    public void tick() {
        super.tick();
        controls();
    }

    @Override
    public void render() {
        super.render();
    }

    private void controls() {
        float xM = 0, zM = 0, yM = 0;

        float movementSpeed = DEFAULT_SPEED;

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
            movementSpeed *= 2;

        float xDir = (float) Math.sin(Math.toRadians(cameraRotation.y)) * movementSpeed,
                zDir = (float) Math.cos(Math.toRadians(cameraRotation.y)) * movementSpeed;

        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            xM -= zDir;
            zM -= xDir;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            xM += zDir;
            zM += xDir;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            xM -= xDir;
            zM += zDir;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            xM += xDir;
            zM -= zDir;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
            yM -= DEFAULT_SPEED;
        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE))
            yM += DEFAULT_SPEED;

        move(xM, yM, zM);

        rotateCamera(
                MouseListener.getDY() * Camera.get().sensitivity,
                MouseListener.getDX() * Camera.get().sensitivity,
                0);

        if (MouseListener.getScrollY()>0) {
            selectedBlock = Integer.toString(Integer.valueOf(selectedBlock)+1);
            if (Integer.valueOf(selectedBlock)>= Block.blocks.length)
                selectedBlock = "1";
            System.out.println(Block.blocks[Integer.valueOf(selectedBlock)].getName());
        }
        if (MouseListener.getScrollY()<0) {
            selectedBlock = Integer.toString(Integer.valueOf(selectedBlock)-1);
            if (Integer.valueOf(selectedBlock)<1)
                selectedBlock = Integer.toString(Block.blocks.length-1);
            System.out.println(Block.blocks[Integer.valueOf(selectedBlock)].getName());
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_Q)) {
            //handler.getWorld().getEventManager().placeBlock(castRay(transform.position, cameraRotation, 4), selectedBlock);
            handler.getWorld().getEventManager().placeBlock(transform.position,selectedBlock);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
            handler.getWorld().getEventManager().breakBlock(transform.position);
        }
    }
}
