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
    public void attackTarget(BQCharacter target) {
        BQCharacter actor = getActor();
        actor.getPositionController().setPosition(PositionUtil.getRandomPositionNear(target));

        JsonArray attackPacket = new JsonArray(new Object[]{Packet.ATTACK, actor.getId(), target.getId()});
        BroadcastUtil.toKnownPlayers(actor, attackPacket.encode());
    }
}
