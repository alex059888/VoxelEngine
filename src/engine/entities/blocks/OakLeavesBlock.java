package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class OakLeavesBlock extends Block {

    public OakLeavesBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Oak leaves block", id);
        float tc[][] = new float[][] {
                {8,0},
                {8,0},
                {8,0},
                {8,0},
                {8,0},
                {8,0}
        };
        setTexCords(tc);
        setProps(true,false,false,false, false);
    }
}
