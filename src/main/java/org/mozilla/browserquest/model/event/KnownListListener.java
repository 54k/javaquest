package org.mozilla.browserquest.model.event;

import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface KnownListListener {

    void onObjectAddedToKnownList(BQObject object);

    void onObjectRemovedFromKnownList(BQObject object);
}
