package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.gameserver.service.WorldService;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.util.BroadcastUtil;
import org.mozilla.browserquest.util.PositionUtil;
import org.vertx.java.core.json.JsonArray;

public class StartAttack extends ClientPacket {

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
        BaseObject target = worldService.findObject(this.target);

        if (target instanceof CharacterObject) {
            player.getPositionController().updatePosition(PositionUtil.getRandomPositionNear(target));

            JsonArray attackPacket = new JsonArray(new Object[]{ClientPacket.ATTACK, player.getId(), target.getId()});
            BroadcastUtil.toKnownPlayers(player, attackPacket.encode());
        }
    }
}
