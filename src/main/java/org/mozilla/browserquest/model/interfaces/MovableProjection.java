package org.mozilla.browserquest.model.interfaces;

import org.mozilla.browserquest.actor.Actor.Projection;

@Projection
public interface MovableProjection {

    Movable asMovable();
}
