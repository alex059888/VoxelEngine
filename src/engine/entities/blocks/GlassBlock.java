package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class GlassBlock extends Block {

    public GlassBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Glass block", id);
        float tc[][] = new float[][] {
                {9,0},
                {9,0},
                {9,0},
                {9,0},
                {9,0},
                {9,0}
        };
        setTexCords(tc);
        setProps(true,false,true,false, false);
    }
}
