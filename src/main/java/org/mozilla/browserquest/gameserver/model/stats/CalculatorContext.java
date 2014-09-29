package org.mozilla.browserquest.gameserver.model.stats;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;

public class CalculatorContext {

    private int result;
    private CharacterObject character;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public CharacterObject getCharacter() {
        return character;
    }

    public void setCharacter(CharacterObject character) {
        this.character = character;
    }
}
