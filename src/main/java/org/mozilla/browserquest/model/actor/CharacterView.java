package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ComponentView;
import org.mozilla.browserquest.model.combat.CombatController;
import org.mozilla.browserquest.model.inventory.InventoryController;
import org.mozilla.browserquest.model.position.MovementController;
import org.mozilla.browserquest.model.stats.StatsCalculator;
import org.mozilla.browserquest.model.stats.StatsController;
import org.mozilla.browserquest.model.status.StatusController;

@ComponentView
public interface CharacterView {

    MovementController getMovementController();

    CombatController getCombatController();

    StatusController getStatusController();

    StatsController getStatsController();

    StatsCalculator getStatsCalculator();

    InventoryController getInventoryController();
}
