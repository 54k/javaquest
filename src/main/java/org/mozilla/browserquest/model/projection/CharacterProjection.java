package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.ActorProjection;
import org.mozilla.browserquest.model.combat.CombatController;
import org.mozilla.browserquest.model.position.MovementController;
import org.mozilla.browserquest.model.status.StatusController;

@ActorProjection
public interface CharacterProjection {

    MovementController getMovementController();

    CombatController getCombatController();

    StatusController getStatusController();
}
