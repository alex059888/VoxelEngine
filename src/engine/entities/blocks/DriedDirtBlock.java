package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class DriedDirtBlock extends Block {
    public DriedDirtBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Dried dirt block", id);
        float tc[][] = new float[][]{
                {13, 0},
                {13, 0},
                {13, 0},
                {13, 0},
                {13, 0},
                {13, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
