package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.gameserver.model.BQWorld;
import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

public class StartAttack extends Packet {

    @LazyInject
    private BQWorld world;

    private int target;

    @Override
    public void setData(Object[] data) {
        target = (int) data[0];
    }

    @Override
    public void run() {
        BQPlayer player = getConnection().getPlayer();
        BQObject target = world.findObject(this.target);

        if (target instanceof BQCharacter) {
            player.getPositionController().updatePosition(PositionUtil.getRandomPositionNear(target));

            JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, player.getId(), target.getId()});
            BroadcastUtil.toKnownPlayers(player, attackPacket.encode());
        }
    }
}
