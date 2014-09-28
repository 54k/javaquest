package org.mozilla.browserquest.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.stats.Calculator;
import org.mozilla.browserquest.model.stats.Calculators;
import org.mozilla.browserquest.model.stats.Stat;
import org.mozilla.browserquest.model.stats.StatsCalculator;

@ComponentPrototype(StatsCalculator.class)
public class PlayerStatsCalculatorComponent extends Component<BQPlayer> implements StatsCalculator {

    private Calculator[] calculators = Calculators.getCharacterCalculators();

    @Override
    public Calculator getStatsCalculatorFor(Stat stat) {
        return calculators[stat.ordinal()];
    }
}
