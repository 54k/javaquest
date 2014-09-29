package org.mozilla.browserquest.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQCreature;
import org.mozilla.browserquest.model.combat.CombatEventListener;
import org.mozilla.browserquest.model.status.StatusEventListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@ComponentPrototype(CreatureController.class)
public class CreatureControllerComponent extends Component<BQCreature> implements CreatureController, CombatEventListener, StatusEventListener {

    @Override
    public void onAttack(BQCharacter attacker, int damage) {
        BQCreature actor = getActor();

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
    public void onHeal(BQCharacter healer, int amount) {
    }

    @Override
    public void onDamage(BQCharacter attacker, int amount) {
    }

    @Override
    public void onRevive() {
    }

    @Override
    public void onDie(BQCharacter killer) {
        BQCreature actor = getActor();
        actor.getPositionController().decay();
        actor.getDropController().drop();

        BQSpawn spawn = actor.getSpawn();
        if (spawn != null) {
            spawn.respawn(actor);
        }
    }
}
