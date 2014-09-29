package org.mozilla.browserquest.gameserver.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListRange;

@ComponentPrototype(KnownListRange.class)
public class PlayerKnownListRangeComponent extends Component<PlayerObject> implements KnownListRange {

    @Override
    public int getDistanceToForgetObject(BaseObject object) {
        return 20;
    }

    @Override
    public int getDistanceToFindObject(BaseObject object) {
        return 15;
    }
}
