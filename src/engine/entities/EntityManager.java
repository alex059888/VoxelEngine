package engine.entities;

import engine.entities.creatures.Creature;
import engine.entities.creatures.Player;
import engine.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Creature> entities;
    private Handler handler;

    public EntityManager(Handler handler) {
        entities = new ArrayList<>();
        this.handler = handler;
    }

    public EntityManager(List<Creature> entities, Handler handler) {
        this.entities = entities;
        this.handler = handler;
    }

    public List<Creature> getEntities() {
        return entities;
    }

    public Creature getCreature(int id) {
        return entities.get(id);
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public void addEntity(Creature e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public void removeEntity(int id) {
        entities.remove(id);
    }

    public void tick() {
        for (Entity e: entities) {
            e.tick();
        }
    }

    public void render() {
        for (Entity e: entities) {
            if (!(entities.get(0) == e))
                e.render();
        }
    }
}
