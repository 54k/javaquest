package org.mozilla.browserquest.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.knownlist.KnownListRange;

@ComponentPrototype(KnownListRange.class)
public class PlayerKnownListRangeComponent extends Component<BQPlayer> implements KnownListRange {

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 20;
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 15;
    }
}
