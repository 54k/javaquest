package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.gameserver.service.WorldService;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.vertx.java.core.json.JsonArray;

public class MobAttack extends ClientPacket {

    @LazyInject
    private WorldService worldService;

    private int target;

    @Override
    public void setData(Object[] data) {
        target = (int) data[0];
    }

    @Override
    public void run() {
        PlayerObject player = getConnection().getPlayer();
        BaseObject attacker = worldService.findObject(this.target);

        if (attacker instanceof CharacterObject) {
            JsonArray attackPacket = new JsonArray(new Object[]{ClientPacket.ATTACK, attacker.getId(), player.getId()});
            BroadcastUtil.toKnownPlayers(attacker, attackPacket.encode());
            CharacterObject attacker1 = (CharacterObject) attacker;
            if (!attacker1.getStatusController().isDead()) {
                attacker1.getCombatController().attack(player);
            }
        }
    }
}
