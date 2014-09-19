package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.Actor.Projection;
import org.mozilla.browserquest.model.actor.behavior.Movable;

@Projection
public interface PlayerProjection {

    Movable asMovable();
}
