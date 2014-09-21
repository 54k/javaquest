package org.mozilla.browserquest.model.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.Heading;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@Prototype(Movable.class)
public class MovableBehavior extends Behavior<BQCharacter> implements Movable {

    @Override
    public void moveTo(int x, int y) {
        BQCharacter actor = getActor();
        actor.asPositionable().setXY(x, y);
        actor.setHeading(Heading.BOTTOM);

        JsonArray jsonArray = new JsonArray(new Object[]{
                Packet.MOVE,
                actor.getId(),
                actor.getX(),
                actor.getY()
        });

        BroadcastUtil.toKnownPlayers(actor, jsonArray.encode());
    }
}
