package org.mozilla.browserquest.model;

import org.mozilla.browserquest.WorldServer;

public class Player extends Character {

    private WorldServer worldServer;

    private boolean hasEnteredInGame;
    private String name;

    public Player() {
        super(-1, "player", "", 0, 0);
    }

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
