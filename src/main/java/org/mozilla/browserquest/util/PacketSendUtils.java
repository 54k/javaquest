package org.mozilla.browserquest.util;

import org.mozilla.browserquest.model.Player;

public final class PacketSendUtils {

    private PacketSendUtils() {
    }

    public static void broadcastToKnownList(Player player, String message) {
        player.getConnection().write(message);
        broadcastToKnownListOthers(player, message);
    }

    public static void broadcastToKnownListOthers(Player player, String message) {
        player.getKnownList().getKnownEntities().forEach(e -> {
            if (e instanceof Player) {
                ((Player) e).getConnection().write(message);
            }
        });
    }

    public static void broadcastToRegion(Player player, String message) {
        player.getWorldRegion().getPlayers().forEach(p -> p.getConnection().write(message));
    }

    public static void broadcastToRegionOthers(Player player, String message) {
        player.getWorldRegion().getPlayers().stream().filter(p -> p != player).forEach(p -> p.getConnection().write(message));
    }
}
