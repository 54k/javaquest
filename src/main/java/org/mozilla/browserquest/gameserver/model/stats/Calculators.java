package org.mozilla.browserquest.gameserver.model.stats;

import org.mozilla.browserquest.gameserver.model.actor.CreatureObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

public final class Calculators {

    private Calculators() {
    }

    public static Calculator[] getCharacterCalculators() {
        Calculator[] calculators = new Calculator[Stat.values().length];

        Calculator maxHpCalculator = new Calculator();
        maxHpCalculator.addFunction(ctx -> {
            PlayerObject player = (PlayerObject) ctx.getCharacter();
            int armor = player.getInventoryController().getArmor();
            ctx.setResult(80 + (armor * 30));
        });
        calculators[Stat.MAX_HP.ordinal()] = maxHpCalculator;

        return calculators;
    }

    public static Calculator[] getCreatureCalculators() {
        Calculator[] calculators = new Calculator[Stat.values().length];

        Calculator maxHpCalculator = new Calculator();
        maxHpCalculator.addFunction(ctx -> {
            int hitPoints = ((CreatureObject) ctx.getCharacter()).getTemplate().getHitPoints();
            ctx.setResult(hitPoints);
        });

        calculators[Stat.MAX_HP.ordinal()] = maxHpCalculator;

        return calculators;
    }
}
