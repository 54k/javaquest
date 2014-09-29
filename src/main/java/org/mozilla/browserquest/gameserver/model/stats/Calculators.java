package org.mozilla.browserquest.gameserver.model.stats;

import org.mozilla.browserquest.gameserver.model.actor.BQCreature;

public final class Calculators {

    private Calculators() {
    }

    public static Calculator[] getCharacterCalculators() {
        Calculator[] calculators = new Calculator[Stat.values().length];

        Calculator maxHpCalculator = new Calculator();
        maxHpCalculator.addFunction(CalculatorContext::getResult);
        calculators[Stat.MAX_HP.ordinal()] = maxHpCalculator;

        return calculators;
    }

    public static Calculator[] getCreatureCalculators() {
        Calculator[] calculators = new Calculator[Stat.values().length];

        Calculator maxHpCalculator = new Calculator();
        maxHpCalculator.addFunction(ctx -> {
            int hitPoints = ((BQCreature) ctx.getCharacter()).getTemplate().getHitPoints();
            ctx.setResult(hitPoints);
        });

        calculators[Stat.MAX_HP.ordinal()] = maxHpCalculator;

        return calculators;
    }
}
