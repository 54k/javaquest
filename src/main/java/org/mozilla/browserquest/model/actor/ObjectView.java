package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ComponentView;
import org.mozilla.browserquest.model.knownlist.KnownList;
import org.mozilla.browserquest.model.knownlist.KnownListRange;
import org.mozilla.browserquest.model.position.PositionController;

@ComponentView
public interface ObjectView {

    PositionController getPositionController();

    KnownList getKnownList();

    KnownListRange getKnownListRange();
}
