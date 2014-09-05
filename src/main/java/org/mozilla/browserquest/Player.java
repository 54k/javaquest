package org.mozilla.browserquest;

public class Player {

    private WorldServer worldServer;

    private int id;

    private boolean hasEnteredInGame;
    private String name;

    private int x;
    private int y;

    public WorldServer getWorldServer() {
        return worldServer;
    }

    public void setWorldServer(WorldServer worldServer) {
        this.worldServer = worldServer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasEnteredInGame() {
        return hasEnteredInGame;
    }

    public void setHasEnteredInGame(boolean hasEnteredInGame) {
        this.hasEnteredInGame = hasEnteredInGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }
}
