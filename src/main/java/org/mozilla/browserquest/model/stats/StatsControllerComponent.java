package org.mozilla.browserquest.model.stats;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;

@ComponentPrototype(StatsController.class)
public class StatsControllerComponent extends Component<BQCharacter> implements StatsController {

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
