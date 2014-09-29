package org.mozilla.browserquest.gameserver.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.actor.BQPlayer;
import org.mozilla.browserquest.gameserver.model.combat.CombatEventListener;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListEventListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@ComponentPrototype(PlayerController.class)
public class PlayerControllerComponent extends Component<BQPlayer> implements PlayerController, KnownListEventListener, CombatEventListener {

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
        damage = 0;
        JsonArray damagePacket = new JsonArray(new Object[]{Packet.DAMAGE, attacker.getId(), damage});
        BroadcastUtil.toSelf(getActor(), damagePacket.encode());
    }
}
