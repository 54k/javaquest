package org.mozilla.browserquest.gameserver.model.knownlist;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface KnownListEventListener {

    void onObjectAddedToKnownList(BaseObject object);

    void onObjectRemovedFromKnownList(BaseObject object);
}
