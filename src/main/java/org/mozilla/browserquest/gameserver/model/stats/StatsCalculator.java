package org.mozilla.browserquest.gameserver.model.stats;

public interface StatsCalculator {

    Calculator getStatsCalculatorFor(Stat stat);
}
