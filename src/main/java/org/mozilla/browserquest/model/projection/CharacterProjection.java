package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.ActorProjection;
import org.mozilla.browserquest.model.controller.CombatController;
import org.mozilla.browserquest.model.controller.MovementController;

@ActorProjection
public interface CharacterProjection {

    MovementController getMovementController();

    CombatController getCombatController();
}