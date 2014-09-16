package org.mozilla.browserquest.model;

import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.SpawnArea;
import org.mozilla.browserquest.knownlist.KnownList;
import org.mozilla.browserquest.knownlist.ObjectKnownList;
import org.mozilla.browserquest.world.WorldInstance;
import org.mozilla.browserquest.world.WorldRegion;

import java.util.Random;

public class BQObject {

    private WorldInstance worldInstance;
    private WorldRegion worldRegion;

    private int id;
    private String type;
    private String kind;
    private int x;
    private int y;

    private SpawnArea spawnArea;
    private KnownList knownList;

    public BQObject(int id, String type, String kind, int x, int y) {
        this.id = id;
        this.type = type;
        this.kind = kind;
        this.x = x;
        this.y = y;
        knownList = newKnownList();
    }

    protected KnownList newKnownList() {
        return new ObjectKnownList(this);
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
    }

    public WorldRegion getWorldRegion() {
        return worldRegion;
    }

    public void setWorldRegion(WorldRegion worldRegion) {
        this.worldRegion = worldRegion;
    }

    public KnownList getKnownList() {
        return knownList;
    }

    public void onObjectAddedToKnownList(BQObject object) {
    }

    public void onObjectRemovedFromKnownList(BQObject object) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
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

    public SpawnArea getSpawnArea() {
        return spawnArea;
    }

    public void setSpawnArea(SpawnArea spawnArea) {
        this.spawnArea = spawnArea;
    }

    public Position getPositionNextTo(BQObject BQEntity) {
        // This is a quick & dirty way to give mobs a random position
        // close to another entity.
        Random random = new Random();
        int r = random.nextInt(4);

        Position position = new Position();
        position.setX(BQEntity.getX());
        position.setY(BQEntity.getY());
        switch (r) {
            case 0:
                position.setY(position.getY() - 1);
            case 1:
                position.setY(position.getY() + 1);
            case 2:
                position.setX(position.getX() - 1);
            case 3:
                position.setX(position.getX() + 1);
        }
        return position;
    }
}
