package org.mozilla.browserquest.model.projection;

import org.mozilla.browserquest.actor.ActorProjection;
import org.mozilla.browserquest.model.position.PositionController;
import org.mozilla.browserquest.model.knownlist.KnownListController;
import org.mozilla.browserquest.model.knownlist.KnownListRangeController;

@ActorProjection
public interface ObjectProjection {

    PositionController getPositionController();

    KnownListController getKnownListController();

    KnownListRangeController getKnownListRangeController();
}
