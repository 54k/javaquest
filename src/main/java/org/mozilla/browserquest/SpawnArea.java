package org.mozilla.browserquest;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.world.WorldInstance;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SpawnArea {

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
    private WorldInstance worldInstance;
    private Set<BQObject> entities = new HashSet<>();

    public SpawnArea(int id, int x, int y, int width, int height, WorldInstance worldInstance) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.worldInstance = worldInstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public void addToArea(BQObject BQEntity) {
        if (entities.add(BQEntity)) {
            BQEntity.setSpawnArea(this);
        }
    }

    protected Position getRandomPositionInsideArea() {
        Random random = new Random();
        Position position;
        do {
            position = new Position(x + random.nextInt(width + 1), y + random.nextInt(height + 1));

        } while (!worldInstance.isValidPosition(position));
        return position;
    }
}
