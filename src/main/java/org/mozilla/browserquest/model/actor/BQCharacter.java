package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.combat.CombatControllerComponent;
import org.mozilla.browserquest.model.player.PlayerKnownListRangeComponent;
import org.mozilla.browserquest.model.inventory.InventoryControllerComponent;
import org.mozilla.browserquest.model.position.MovementControllerComponent;
import org.mozilla.browserquest.model.stats.StatsControllerComponent;
import org.mozilla.browserquest.model.status.StatusControllerComponent;

@ActorPrototype({MovementControllerComponent.class, CombatControllerComponent.class, StatsControllerComponent.class, StatusControllerComponent.class, InventoryControllerComponent.class, PlayerKnownListRangeComponent.class})
public abstract class BQCharacter extends BQObject implements CharacterView {
}
