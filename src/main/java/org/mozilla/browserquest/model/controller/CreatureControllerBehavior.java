package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQCreature;
import org.mozilla.browserquest.model.event.CombatListener;
import org.mozilla.browserquest.model.event.StatusListener;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

@BehaviorPrototype(CreatureController.class)
public class CreatureControllerBehavior extends Behavior<BQCreature> implements CreatureController, CombatListener, StatusListener {

    @Override
    public void onAttack(BQCharacter attacker, int damage) {
        BQCreature actor = getActor();
        JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, actor.getId(), attacker.getId()});
        BroadcastUtil.toKnownPlayers(actor, attackPacket.encode());

        JsonArray damagePacket = new JsonArray(new Object[]{Packet.DAMAGE, attacker.getId(), damage});
        BroadcastUtil.toKnownPlayers(actor, damagePacket.encode());
        actor.getCombatController().attackTarget(attacker);
    }

    @Override
    public void onRevive() {
    }

    @Override
    public void onDie(BQCharacter killer) {
    }
}
