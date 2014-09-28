package org.mozilla.browserquest.model.stats;

public interface StatsCalculator {

    Calculator getStatsCalculatorFor(Stat stat);
}
