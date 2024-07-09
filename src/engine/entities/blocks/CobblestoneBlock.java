package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class CobblestoneBlock extends Block {
    public CobblestoneBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Cobblestone block", id);
        float tc[][] = new float[][]{
                {4, 0},
                {4, 0},
                {4, 0},
                {4, 0},
                {4, 0},
                {4, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
