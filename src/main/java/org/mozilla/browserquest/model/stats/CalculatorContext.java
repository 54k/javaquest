package org.mozilla.browserquest.model.stats;

import org.mozilla.browserquest.model.actor.BQCharacter;

public class CalculatorContext {

    private int result;
    private BQCharacter character;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public BQCharacter getCharacter() {
        return character;
    }

    public void setCharacter(BQCharacter character) {
        this.character = character;
    }
}
