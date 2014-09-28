package org.mozilla.browserquest.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.stats.Calculator;
import org.mozilla.browserquest.model.stats.Calculators;
import org.mozilla.browserquest.model.stats.Stat;
import org.mozilla.browserquest.model.stats.StatsCalculator;

@ComponentPrototype(StatsCalculator.class)
public class CreatureStatsCalculatorComponent extends Component<BQCharacter> implements StatsCalculator {

    private Calculator[] calculators = Calculators.getCreatureCalculators();

    @Override
    public Calculator getStatsCalculatorFor(Stat stat) {
        return calculators[stat.ordinal()];
    }
}
