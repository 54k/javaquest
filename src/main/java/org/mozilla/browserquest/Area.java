package org.mozilla.browserquest;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Area {

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
    private WorldServer worldServer;
    private Set<Entity> entities = new HashSet<>();

    public Area(int id, int x, int y, int width, int height, WorldServer worldServer) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.worldServer = worldServer;
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

    public WorldServer getWorldServer() {
        return worldServer;
    }

    public void setWorldServer(WorldServer worldServer) {
        this.worldServer = worldServer;
    }

    public void addToArea(Entity entity) {
        if (entities.add(entity)) {
            entity.setArea(this);
        }
    }

    protected Position getRandomPositionInsideArea() {
        Random random = new Random();
        Position position;
        do {
            position = new Position(x + random.nextInt(width + 1), y + random.nextInt(height + 1));

        } while (!worldServer.isValidPosition(position));
        return position;
    }
}
