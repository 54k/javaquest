package org.mozilla.browserquest.util;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.network.packet.ServerPacket;

public final class BroadcastUtil {

    private BroadcastUtil() {
    }

    public static void toSelf(PlayerObject object, String packet) {
        object.getConnection().write(packet);
    }

    public static void toKnownPlayers(BaseObject object, String packet) {
        object.getKnownListController().getKnownPlayers().values().forEach(p -> p.getConnection().write(packet));
    }

    public static void toKnownPlayers(BaseObject object, ServerPacket packet) {
        object.getKnownListController().getKnownPlayers().values().forEach(p -> p.getConnection().write(packet));
    }

    public static void toSelfAndKnownPlayers(PlayerObject object, String packet) {
        toSelf(object, packet);
        toKnownPlayers(object, packet);
    }

    public static void toPlayersInRegion(BaseObject object, String packet) {
        object.getPositionController().getRegion().getPlayers().values().stream().filter(p -> p != object).forEach(p -> p.getConnection().write(packet));
    }

    public static void toSelfAndPlayersInRegion(PlayerObject object, String packet) {
        toSelf(object, packet);
        toPlayersInRegion(object, packet);
    }
}