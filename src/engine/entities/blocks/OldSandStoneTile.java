package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class OldSandStoneTile extends Block {
    public OldSandStoneTile(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Old sand stone tile block", id);
        float tc[][] = new float[][]{
                {12, 0},
                {12, 0},
                {12, 0},
                {12, 0},
                {12, 0},
                {12, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
