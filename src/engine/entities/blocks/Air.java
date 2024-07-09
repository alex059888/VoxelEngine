package engine.entities.blocks;

import engine.entities.Block;

public class Air extends Block {
    public Air(String id) {
        super(0, 0, 0, null, null, 0, null, "Air", id);
        setProps(false,false,true,false, true);
    }
}
