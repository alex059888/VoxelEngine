package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class GrassBlock extends Block {

    public GrassBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Grass block", id);
        float tc[][] = new float[][] {
                {1,0},
                {1,0},
                {1,0},
                {1,0},
                {2,0},
                {0,0}
        };
        setTexCords(tc);
        setProps(true,false,false,false, false);
    }
}
