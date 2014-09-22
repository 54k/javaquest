package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.ActorProjection;
import org.mozilla.browserquest.model.controller.PositionController;

@ActorProjection
public interface ObjectProjection {

    PositionController getPositionController();
}
