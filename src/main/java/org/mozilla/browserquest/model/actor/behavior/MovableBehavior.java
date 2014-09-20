package org.mozilla.browserquest.model.actor.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@Prototype(Movable.class)
public class MovableBehavior extends Behavior<BQPlayer> implements Movable {

    @Override
    public void moveTo(int x, int y) {
        BQPlayer actor = getActor();
        actor.asPositionable().setXY(x, y);
        actor.setHeading(2);

        JsonArray jsonArray = new JsonArray(new Object[] {
                Packet.MOVE,
                actor.getId(),
                actor.getX(),
                actor.getY()
        });

        BroadcastUtil.toKnownPlayers(actor, jsonArray.encode());
    }
}
