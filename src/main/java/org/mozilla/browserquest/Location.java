package org.mozilla.browserquest;

import java.util.Random;

public class Location {

    private int id;
    private int x;
    private int y;
    private int w;
    private int h;

    public Location(int id, int x, int y, int w, int h) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Position getRandomPosition() {
        Random random = new Random();
        return new Position(x + random.nextInt(w - 1), y + random.nextInt(h - 1));
    }
}
