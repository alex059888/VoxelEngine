package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class ChiseledStone1 extends Block {
    public ChiseledStone1(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Chiseled stone block 1", id);
        float tc[][] = new float[][]{
                {11, 0},
                {11, 0},
                {11, 0},
                {11, 0},
                {11, 0},
                {11, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
