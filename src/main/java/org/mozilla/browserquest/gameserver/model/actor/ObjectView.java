package org.mozilla.browserquest.gameserver.model.actor;

import org.mozilla.browserquest.actor.ActorView;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownList;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListRange;
import org.mozilla.browserquest.gameserver.model.position.PositionController;

@ActorView
public interface ObjectView {

    PositionController getPositionController();

    KnownList getKnownList();

    KnownListRange getKnownListRange();
}
