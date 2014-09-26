package org.mozilla.browserquest.model.knownlist;

import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface KnownListEventListener {

    void onObjectAddedToKnownList(BQObject object);

    void onObjectRemovedFromKnownList(BQObject object);
}
