package engine.entities.blocks;

import engine.entities.Block;
import org.joml.Vector4f;

public class SandBlock extends Block {

    public SandBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null, "Sand block", id);
        float tc[][] = new float[][] {
                {7,0},
                {7,0},
                {7,0},
                {7,0},
                {7,0},
                {7,0}
        };
        setTexCords(tc);
        setProps(true,false,false,true, false);
    }
}
