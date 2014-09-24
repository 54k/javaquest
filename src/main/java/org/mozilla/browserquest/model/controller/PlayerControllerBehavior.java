package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.model.event.CombatListener;
import org.mozilla.browserquest.model.event.KnownListListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@BehaviorPrototype(PlayerController.class)
public class PlayerControllerBehavior extends Behavior<BQPlayer> implements PlayerController, KnownListListener, CombatListener {

    @Override
    public void onObjectAddedToKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.SPAWN);
        object.getInfo().forEach(spawnPacket::add);
        BroadcastUtil.toSelf(getActor(), spawnPacket.encode());
    }

    @Override
    public void onObjectRemovedFromKnownList(BQObject object) {
        JsonArray spawnPacket = new JsonArray(new Object[]{Packet.DESPAWN, object.getId()});
        BroadcastUtil.toSelf(getActor(), spawnPacket.encode());
    }

    @Override
    public void onAttack(BQCharacter attacker, int damage) {
        JsonArray damagePacket = new JsonArray(new Object[]{Packet.HURT, attacker.getId(), damage});
        BroadcastUtil.toSelf(getActor(), damagePacket.encode());
    }
}
