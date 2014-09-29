package org.mozilla.browserquest.gameserver.model.stats;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;

@ComponentPrototype(StatsController.class)
public class StatsControllerComponent extends Component<CharacterObject> implements StatsController {

    @Override
    public int getMaxHitPoints() {
        return calcStat(Stat.MAX_HP, 0);
    }

    private int calcStat(Stat stat, int initial) {
        CalculatorContext calculatorContext = new CalculatorContext();
        calculatorContext.setCharacter(getActor());
        calculatorContext.setResult(initial);
        Calculator statsCalculator = getActor().getStatsCalculator().getStatsCalculatorFor(stat);
        statsCalculator.calculate(calculatorContext);
        return calculatorContext.getResult();
    }
}
