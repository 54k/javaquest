package org.mozilla.browserquest.network.packet.client;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.network.packet.Packet;

public class StartAttack extends Packet {

    @Inject
    private BQWorld world;

    private int target;

    @Override
    public void setData(Object[] data) {
        target = (int) data[0];
    }

    @Override
    public void run() {
        BQPlayer player = getConnection().getPlayer();
        BQObject target = world.findObject(this.target);

        if (target instanceof BQCharacter) {
            player.getCombatController().setTarget((BQCharacter) target);
        }
    }
}
