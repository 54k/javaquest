package org.mozilla.browserquest.gameserver.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.gameserver.model.combat.CombatControllerComponent;
import org.mozilla.browserquest.gameserver.model.player.PlayerKnownListRangeComponent;
import org.mozilla.browserquest.gameserver.model.inventory.InventoryControllerComponent;
import org.mozilla.browserquest.gameserver.model.position.MovementControllerComponent;
import org.mozilla.browserquest.gameserver.model.stats.StatsControllerComponent;
import org.mozilla.browserquest.gameserver.model.status.StatusControllerComponent;

@ActorPrototype({MovementControllerComponent.class, CombatControllerComponent.class, StatsControllerComponent.class, StatusControllerComponent.class, InventoryControllerComponent.class, PlayerKnownListRangeComponent.class})
public abstract class BQCharacter extends BQObject implements CharacterView {
}
