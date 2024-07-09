package engine.entities;

import engine.entities.blocks.*;
import engine.gfx.meshes.Mesh;
import engine.gfx.textures.TexSizes;
import engine.gfx.textures.Texture;
import engine.handler.Handler;
import engine.world.Chunk;
import org.joml.Random;
import org.joml.Vector4f;

public class Block extends Entity {
    protected String name;
    protected static final float BlockSize = 1;
    public float texCords[][];
    public Vector4f color;
    public final String id;
    protected boolean useGravity = false;
    protected Chunk chunk;

    protected boolean isSolid, isLiquid, isTransparent, hasGravity, isAGas;

    public Block(float x, float y, float z, Vector4f color, Handler handler, int texID, float texCords[][], String name, String id) {
        super(x, y, z, handler, texID);
        this.name = name;
        this.texCords = texCords;
        this.color = color;
        this.id = id;
    }

    public Block(Block block) {
        super(block.transform.position.x, block.transform.position.y, block.transform.position.z,block.getHandler(), block.texID);
        this.texCords = block.texCords;
        this.color = block.color;
        this.id = block.id;
        setProps(block.isSolid(), block.isLiquid(), block.isTransparent(), block.hasGravity(), block.isAGas());
    }

    @Override
    public void render() {
        if(mesh!=null) {
            Texture.getTexture(texID).bind();
            mesh.render();
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void destroyMesh() {
        mesh = null;
    }

    public void genMesh() {
        float[] vboS = new float[9*4*6];
        int[] eboS = new int[36];
        int i = 0;
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getFrontFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getBackFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getLeftFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getRightFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getTopFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 9*4; j++) {
            vboS[i] = getBottomFace(0,0,0,0)[j];
            i++;
        }
        for (int j = 0; j < 36; j++) {
            eboS[j] = getEboS(j/6)[j%6];
        }
        mesh = new Mesh(vboS, eboS);
    }

    public float[] getFrontFace(float c1, float c2, float c3, float c4) {
        return new float[] {
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[0][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[0][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[0][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[0][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[0][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[0][1], TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[0][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[0][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public float[] getBackFace(float c1, float c2, float c3, float c4) {
        return new float[] {
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[3][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[3][1], TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[3][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[3][1]+1, TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[3][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[3][1]+1, TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[3][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[3][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public float[] getLeftFace(float c1, float c2, float c3, float c4) {
        return new float[]{
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[1][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[1][1], TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[1][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[1][1]+1, TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[1][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[1][1]+1, TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[1][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[1][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public float[] getRightFace(float c1, float c2, float c3, float c4) {
        return new float[] {
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[2][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[2][1], TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[2][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[2][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[2][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[2][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[2][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[2][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public float[] getTopFace(float c1, float c2, float c3, float c4) {
        return new float[] {
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[4][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1], TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[4][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[4][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[4][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public float[] getBottomFace(float c1, float c2, float c3, float c4) {
        return new float[] {
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c1, color.y-c1, color.z-c1, color.w,
                Texture.getTexCoord(texCords[5][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[5][1], TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c2, color.y-c2, color.z-c2, color.w,
                Texture.getTexCoord(texCords[5][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[5][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c3, color.y-c3, color.z-c3, color.w,
                Texture.getTexCoord(texCords[5][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[5][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x%Chunk.SIZE,
                -0.5f * BlockSize + transform.position.y%Chunk.SIZE,
                0.5f * BlockSize + transform.position.z%Chunk.SIZE,
                color.x-c4, color.y-c4, color.z-c4, color.w,
                Texture.getTexCoord(texCords[5][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[5][1], TexSizes.X512,TexSizes.X16)
        };
    }

    public static int[] getEboS(int multiplier) {
        return new int[] {1+multiplier*4, multiplier*4, 2+multiplier*4,
                3+multiplier*4, 2+multiplier*4, multiplier*4};
    }

    public String getId() {
        return id;
    }

    public Block setParams(float x, float y, float z, Handler handler, Chunk chunk) {
        setPosition(x,y,z);
        this.handler = handler;
        this.chunk = chunk;
        return this;
    }

    protected void setTexCords(float texCords[][]) {
        this.texCords = texCords;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public boolean isAGas() {
        return isAGas;
    }

    public static Block blocks[] = {
            new Air("0"),
            new DirtBlock("1"),
            new GrassBlock("2"),
            new StoneBlock("3"),
            new CobblestoneBlock("4"),
            new OakLogBlock("5"),
            new SandBlock("6"),
            new OakLeavesBlock("7"),
            new GlassBlock("8"),
            new WaterBlock("9"),
            new OldSandStoneTile("10"),
            new DriedDirtBlock("11"),
            new ChiseledStone1("12"),
            new ChiseledStone2("13")
    };

    public static int getRandBlockId() {
        return new Random().nextInt(blocks.length);
    }

    public static String getRandBlockIdFromList(String[] ids) {
        return ids[new Random().nextInt(ids.length)];
    }

    public void setProps(boolean isSolid, boolean isLiquid, boolean isTransparent, boolean hasGravity, boolean isAGas) {
        this.isSolid = isSolid;
        this.isAGas = isAGas;
        this.isLiquid = isLiquid;
        this.isTransparent = isTransparent;
        this.hasGravity = hasGravity;
    }

    public static Block getBlock(float x, float y, float z, Handler handler, Chunk chunk, String id) {
        for (Block b: blocks) {
            if (b.id.equals(id))
                return new Block(b.setParams(x, y, z, handler,chunk));
        }
        return blocks[0].setParams(x,y,z,handler,chunk);
    }

    public void update() {
        handler.getWorld().getEventManager().updateChunkMesh(chunk.getId());
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
