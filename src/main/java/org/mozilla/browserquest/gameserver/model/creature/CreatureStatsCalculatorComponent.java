package org.mozilla.browserquest.gameserver.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;
import org.mozilla.browserquest.gameserver.model.stats.Calculator;
import org.mozilla.browserquest.gameserver.model.stats.Calculators;
import org.mozilla.browserquest.gameserver.model.stats.Stat;
import org.mozilla.browserquest.gameserver.model.stats.StatsCalculator;

@ComponentPrototype(StatsCalculator.class)
public class CreatureStatsCalculatorComponent extends Component<BQCharacter> implements StatsCalculator {

    private Calculator[] calculators = Calculators.getCreatureCalculators();

    @Override
    public Calculator getStatsCalculatorFor(Stat stat) {
        return calculators[stat.ordinal()];
    }
}
