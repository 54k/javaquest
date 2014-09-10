package org.mozilla.browserquest;

import java.util.Random;

public class Entity {

    private int id;
    private String type;
    private String kind;
    private int x;
    private int y;

    private Area area;

    public Entity(int id, String type, String kind, int x, int y) {
        this.id = id;
        this.type = type;
        this.kind = kind;
        this.x = x;
        this.y = y;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Position getPositionNextTo(Entity entity) {
        // This is a quick & dirty way to give mobs a random position
        // close to another entity.
        Random random = new Random();
        int r = random.nextInt(4);

        Position position = new Position();
        position.setX(entity.getX());
        position.setY(entity.getY());
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
