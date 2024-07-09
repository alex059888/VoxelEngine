package engine.entities.blocks;

import engine.entities.Block;
import engine.gfx.textures.TexSizes;
import engine.gfx.textures.Texture;
import org.joml.Vector4f;

public class WaterBlock extends Block {
    public WaterBlock(String id) {
        super(0, 0, 0, new Vector4f(1, 1, 1, 0.7f), null, 0, null,"Water block", id);
        float tc[][] = new float[][]{
                {10, 0},
                {10, 0},
                {10, 0},
                {10, 0},
                {10, 0},
                {10, 0}
        };
        setTexCords(tc);
        setProps(false, true, true, false, false);
    }

    @Override
    public float[] getTopFace(float c1, float c2, float c3, float c4) {
        if (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isSolid()))
            return getTopFaceSurface();
        return super.getTopFace(0,0,0,0);
    }

    @Override
    public float[] getRightFace(float c1, float c2, float c3, float c4) {
        if ((handler.getWorld().getBlock(transform.position.x+1,transform.position.y,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x+1,transform.position.y,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x+1,transform.position.y,transform.position.z).isSolid()))
        && (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isSolid())))
            return getRightFaceSurface();
        return super.getRightFace(0,0,0,0);
    }

    @Override
    public float[] getLeftFace(float c1, float c2, float c3, float c4) {
        if ((handler.getWorld().getBlock(transform.position.x-1,transform.position.y,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x-1,transform.position.y,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x-1,transform.position.y,transform.position.z).isSolid()))
        && (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isSolid())))
            return getLeftFaceSurface();
        return super.getLeftFace(0,0,0,0);
    }

    @Override
    public float[] getFrontFace(float c1, float c2, float c3, float c4) {
        if ((handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z+1).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z+1).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z+1).isSolid()))
        && (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isSolid())))
            return getFrontFaceSurface();
        return super.getFrontFace(0,0,0,0);
    }

    @Override
    public float[] getBackFace(float c1, float c2, float c3, float c4) {
        if ((handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z-1).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z-1).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y,transform.position.z-1).isSolid()))
            && (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isAGas() ||
                (handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isTransparent() &&
                        handler.getWorld().getBlock(transform.position.x,transform.position.y+1,transform.position.z).isSolid())))
            return getBackFaceSurface();
        return super.getBackFace(0,0,0,0);
    }

    public float[] getBackFaceSurface() {
        return new float[]{
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[3][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[3][1], TexSizes.X512, TexSizes.X16) - 0.1f,
                0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[3][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[3][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[3][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[3][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[3][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[3][1], TexSizes.X512, TexSizes.X16) - 0.1f
        };
    }

    public float[] getFrontFaceSurface() {
        return new float[]{
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[0][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[0][1], TexSizes.X512, TexSizes.X16) - 0.1f,
                0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[0][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[0][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[0][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[0][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[0][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[0][1], TexSizes.X512, TexSizes.X16) - 0.1f
        };
    }

    public float[] getLeftFaceSurface() {
        return new float[]{
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[1][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[1][1], TexSizes.X512, TexSizes.X16) - 0.1f,
                -0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[1][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[1][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[1][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[1][1] + 1, TexSizes.X512, TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[1][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[1][1], TexSizes.X512, TexSizes.X16) - 0.1f
        };
    }

    public float[] getRightFaceSurface() {
        return new float[]{
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[2][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[2][1], TexSizes.X512, TexSizes.X16)-0.1f,
                0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[2][0], TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[2][1] + 1, TexSizes.X512, TexSizes.X16),
                0.5f * BlockSize + transform.position.x,
                -0.5f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[2][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[2][1] + 1, TexSizes.X512, TexSizes.X16),
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[2][0] + 1, TexSizes.X512, TexSizes.X16),
                Texture.getTexCoord(texCords[2][1], TexSizes.X512, TexSizes.X16)-0.1f
        };
    }

    public float[] getTopFaceSurface() {
        return new float[] {
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[4][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1], TexSizes.X512,TexSizes.X16),
                -0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[4][0], TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[4][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1]+1, TexSizes.X512,TexSizes.X16),
                0.5f * BlockSize + transform.position.x,
                0.4f * BlockSize + transform.position.y,
                -0.5f * BlockSize + transform.position.z,
                color.x, color.y, color.z, color.w,
                Texture.getTexCoord(texCords[4][0]+1, TexSizes.X512,TexSizes.X16),
                Texture.getTexCoord(texCords[4][1], TexSizes.X512,TexSizes.X16)
        };
    }
}
