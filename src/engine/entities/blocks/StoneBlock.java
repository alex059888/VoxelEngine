package engine.entities.blocks;
import engine.entities.Block;
import org.joml.Vector4f;

public class StoneBlock extends Block {
    public StoneBlock(String id) {
        super(0, 0, 0, new Vector4f(1,1,1,1), null, 0, null,"Stone block", id);
        float tc[][] = new float[][]{
                {3, 0},
                {3, 0},
                {3, 0},
                {3, 0},
                {3, 0},
                {3, 0}
        };
        setTexCords(tc);
        setProps(true, false, false, false, false);
    }
}
