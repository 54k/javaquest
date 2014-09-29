package org.mozilla.browserquest.gameserver.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.model.combat.CombatEventListener;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListEventListener;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.gameserver.model.status.StatusController;
import org.mozilla.browserquest.gameserver.model.status.StatusEventListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@ComponentPrototype(PlayerController.class)
public class PlayerControllerComponent extends Component<PlayerObject> implements PlayerController, KnownListEventListener, CombatEventListener, StatusEventListener {

    @Override
    public void onObjectAddedToKnownList(BaseObject object) {
        JsonArray spawnPacket = new JsonArray();
        spawnPacket.addNumber(Packet.SPAWN);
        object.getInfo().forEach(spawnPacket::add);
        BroadcastUtil.toSelf(getActor(), spawnPacket.encode());
    }

    @Override
    public void onObjectRemovedFromKnownList(BaseObject object) {
        JsonArray spawnPacket = new JsonArray(new Object[]{Packet.DESPAWN, object.getId()});
        BroadcastUtil.toSelf(getActor(), spawnPacket.encode());
    }

    @Override
    public void onAttack(CharacterObject attacker, int damage) {
        StatusController statusController = getActor().getStatusController();
        if (statusController.isDead()) {
            return;
        }

        statusController.damage(attacker, damage);
        JsonArray damagePacket = new JsonArray(new Object[]{Packet.DAMAGE, attacker.getId(), damage});
        BroadcastUtil.toSelf(getActor(), damagePacket.encode());
    }

    @Override
    public void onHeal(CharacterObject healer, int amount) {

    }

    @Override
    public void onDamage(CharacterObject attacker, int amount) {

    }

    @Override
    public void onRevive() {

    }

    @Override
    public void onDie(CharacterObject killer) {
        PositionController positionController = getActor().getPositionController();
        positionController.decay();
        JsonArray spawnPacket = new JsonArray(new Object[]{Packet.DESPAWN, getActor().getId()});
        BroadcastUtil.toSelf(getActor(), spawnPacket.encode());
    }
}
