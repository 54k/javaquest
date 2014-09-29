package org.mozilla.browserquest.gameserver.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.Spawn;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.CreatureObject;
import org.mozilla.browserquest.gameserver.model.combat.CombatEventListener;
import org.mozilla.browserquest.gameserver.model.status.StatusEventListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@ComponentPrototype(CreatureController.class)
public class CreatureControllerComponent extends Component<CreatureObject> implements CreatureController, CombatEventListener, StatusEventListener {

    @Override
    public void onAttack(CharacterObject attacker, int damage) {
        CreatureObject actor = getActor();

        JsonArray damagePacket = new JsonArray(new Object[]{Packet.DAMAGE, attacker.getId(), damage});
        BroadcastUtil.toKnownPlayers(actor, damagePacket.encode());

        getActor().getStatusController().damage(attacker, damage);

        if (actor.getStatusController().isDead()) {
            return;
        }

        JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, actor.getId(), attacker.getId()});
        BroadcastUtil.toKnownPlayers(actor, attackPacket.encode());
        actor.getCombatController().attack(attacker);
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
        CreatureObject actor = getActor();
        actor.getPositionController().decay();
        actor.getDropController().drop();

        Spawn spawn = actor.getSpawn();
        if (spawn != null) {
            spawn.respawn(actor);
        }
    }
}
