package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class ChiseledStone2 extends Block {
    public ChiseledStone2(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Chiseled stone block 2", id);
        float tc[][] = new float[][]{
                {14, 0},
                {14, 0},
                {14, 0},
                {14, 0},
                {14, 0},
                {14, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
