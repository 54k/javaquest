package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface KnownListEventListener {

    void onObjectAddedToKnownList(BQObject object);

    void onObjectRemovedFromKnownList(BQObject object);
}
