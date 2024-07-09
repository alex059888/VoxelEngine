package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class OakLogBlock extends Block {
    public OakLogBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Oak log block", id);
        float tc[][] = new float[][]{
                {5, 0},
                {5, 0},
                {5, 0},
                {5, 0},
                {6, 0},
                {6, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
