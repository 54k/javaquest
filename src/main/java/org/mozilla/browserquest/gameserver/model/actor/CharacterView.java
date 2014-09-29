package org.mozilla.browserquest.gameserver.model.actor;

import org.mozilla.browserquest.actor.ActorView;
import org.mozilla.browserquest.gameserver.model.combat.CombatController;
import org.mozilla.browserquest.gameserver.model.inventory.InventoryController;
import org.mozilla.browserquest.gameserver.model.position.MovementController;
import org.mozilla.browserquest.gameserver.model.stats.StatsCalculator;
import org.mozilla.browserquest.gameserver.model.stats.StatsController;
import org.mozilla.browserquest.gameserver.model.status.StatusController;

@ActorView
public interface CharacterView {

    MovementController getMovementController();

    CombatController getCombatController();

    StatusController getStatusController();

    StatsController getStatsController();

    StatsCalculator getStatsCalculator();

    InventoryController getInventoryController();
}
