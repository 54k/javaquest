package org.mozilla.browserquest.model.actor.projection;

import org.mozilla.browserquest.actor.Actor.Projection;
import org.mozilla.browserquest.model.actor.behavior.Movable;

@Projection
public interface CharacterProjection {

    Movable asMovable();
}
