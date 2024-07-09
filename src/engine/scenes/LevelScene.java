package engine.scenes;

import engine.gfx.Camera;
import engine.gfx.shaders.DefaultShader;
import engine.gfx.shaders.Shader;
import engine.gfx.textures.Texture;
import engine.handler.Handler;
import engine.world.World;
import engine.world.worldTipes.LimitedWorld.SmallWorld;
import main.Game;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class LevelScene extends Scene{
    private World world;
    private Shader defaultShader;

    public LevelScene(Handler handler, String worldName) {
        super(handler);
        defaultShader = new DefaultShader();
        handler.setDefaultShader(defaultShader);
        world = new SmallWorld(handler,8,4,8);
        handler.setWorld(world);
        camera = new Camera(world.getEntityManager().getCreature(0));
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render() {
        defaultShader.bind();
        defaultShader.setTexture("tex", 0);
        glActiveTexture(GL_TEXTURE0);
        Texture.getTexture(0).bind();
        defaultShader.setProjection(Camera.get().getProjection());
        defaultShader.setView(Camera.get().getView());
        world.render();
        Shader.unbind();
    }
}
