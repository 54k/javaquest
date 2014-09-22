package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

public class StartAttack extends Packet {

    @Inject
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
            player.getPositionController().setPosition(PositionUtil.getRandomPositionNear(target));

            JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, player.getId(), target.getId()});
            BroadcastUtil.toKnownPlayers(player, attackPacket.encode());
        }
    }
}
