package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.BehaviorPrototype;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.network.packet.Packet;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

@BehaviorPrototype(CombatController.class)
public class CombatControllerBehavior extends Behavior<BQCharacter> implements CombatController {

    @Override
    public void setTarget(BQCharacter target) {
        BQCharacter actor = getActor();

        BQCharacter oldTarget = actor.getTarget();
        if (oldTarget != null) {
            oldTarget.onStopAttacking(actor);
        }

        actor.setTarget(target);
        target.onStartAttacking(actor);

        actor.getPositionController().setPosition(PositionUtil.getRandomPositionNear(target));
        JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, actor.getId(), actor.getTarget().getId()});
        BroadcastUtil.toKnownPlayers(actor, attackPacket.encode());
    }
}
