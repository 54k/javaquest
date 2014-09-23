package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

public class MobAttack extends Packet {

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
        BQObject attacker = world.findObject(this.target);

        if (attacker instanceof BQCharacter) {
            JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, attacker.getId(), player.getId()});
            BroadcastUtil.toKnownPlayers(attacker, attackPacket.encode());
            ((BQCharacter) attacker).getCombatController().attackTarget(player);
        }
    }
}
