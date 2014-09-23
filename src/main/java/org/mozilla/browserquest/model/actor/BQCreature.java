package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype
public abstract class BQCreature extends BQCharacter {

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 7;
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 5;
    }

    @Override
    public void onHit(BQCharacter attacker, int damage) {
        JsonArray damagePacket = new JsonArray(new Object[]{Packet.DAMAGE, attacker.getId(), damage});
        BroadcastUtil.toKnownPlayers(this, damagePacket.encode());
        getCombatController().attackTarget(attacker);
    }
}
