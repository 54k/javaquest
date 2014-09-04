package org.mozilla.browserquest;

public class Player {

    private WorldServer worldServer;

    private boolean hasEnteredInGame;
    private String name;

    public WorldServer getWorldServer() {
        return worldServer;
    }

    public void setWorldServer(WorldServer worldServer) {
        this.worldServer = worldServer;
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
}
