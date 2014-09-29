package org.mozilla.browserquest.gameserver.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.model.stats.Calculator;
import org.mozilla.browserquest.gameserver.model.stats.Calculators;
import org.mozilla.browserquest.gameserver.model.stats.Stat;
import org.mozilla.browserquest.gameserver.model.stats.StatsCalculator;

@ComponentPrototype(StatsCalculator.class)
public class PlayerStatsCalculatorComponent extends Component<PlayerObject> implements StatsCalculator {

    private Calculator[] calculators = Calculators.getCharacterCalculators();

    @Override
    public Calculator getStatsCalculatorFor(Stat stat) {
        return calculators[stat.ordinal()];
    }
}
