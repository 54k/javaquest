package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.Actor.Projection;
import org.mozilla.browserquest.model.behavior.Movable;

@Projection
public interface CharacterProjection {

    Movable asMovable();
}
