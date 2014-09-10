package org.mozilla.browserquest.model;

import org.mozilla.browserquest.WorldInstance;

public class Player extends Character {

    private WorldInstance worldInstance;

    private boolean hasEnteredInGame;
    private String name;

    public Player() {
        super(-1, "player", "", 0, 0);
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public void setWorldInstance(WorldInstance worldInstance) {
        this.worldInstance = worldInstance;
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
