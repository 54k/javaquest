package org.mozilla.browserquest.util;

import org.mozilla.browserquest.model.actor.BQObject;
import org.mozilla.browserquest.model.actor.BQPlayer;

public final class BroadcastUtil {

    private BroadcastUtil() {
    }

    public static void toSelf(BQPlayer object, String packet) {
        object.getConnection().write(packet);
    }

    public static void toKnownPlayers(BQObject object, String packet) {
        object.getKnownListController().getKnownPlayers().values().forEach(p -> p.getConnection().write(packet));
    }

    public static void toSelfAndKnownPlayers(BQPlayer object, String packet) {
        toSelf(object, packet);
        toKnownPlayers(object, packet);
    }

    public static void toPlayersInRegion(BQObject object, String packet) {
        object.getRegion().getPlayers().values().stream().filter(p -> p != object).forEach(p -> p.getConnection().write(packet));
    }

    public static void toSelfAndPlayersInRegion(BQPlayer object, String packet) {
        toSelf(object, packet);
        toPlayersInRegion(object, packet);
    }
}