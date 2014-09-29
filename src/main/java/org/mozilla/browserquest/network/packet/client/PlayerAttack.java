package org.mozilla.browserquest.network.packet.client;

import org.mozilla.browserquest.gameserver.model.World;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.network.packet.Packet;

public class PlayerAttack extends Packet {

    @LazyInject
    private World world;

    private int target;

    @Override
    public void setData(Object[] data) {
        target = (int) data[0];
    }

    @Override
    public void run() {
        PlayerObject player = getConnection().getPlayer();
        BaseObject target = world.findObject(this.target);
        if (target instanceof CharacterObject) {
            CharacterObject target1 = (CharacterObject) target;
            if (!target1.getStatusController().isDead()) {
                player.getCombatController().attack(target1);
            }
        }
    }
}
