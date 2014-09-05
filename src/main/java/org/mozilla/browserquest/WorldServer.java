package org.mozilla.browserquest;

import org.mozilla.browserquest.network.NetworkServer;

import java.util.HashSet;
import java.util.Set;

public class WorldServer {

    private String name;
    private int maxPlayers;
    private int playersCount;

    private NetworkServer server;

    private Set<Player> players = new HashSet<>();

    public WorldServer(String name, int maxPlayers, NetworkServer server) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.server = server;
    }

    public void addPlayer(Player player) {
        if (players.add(player)) {
            player.setWorldServer(this);
            playersCount++;
        }
    }

    public void removePlayer(Player player) {
        if (players.remove(player)) {
            player.setWorldServer(null);
            playersCount--;
        }
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void run(Map map) {

    }
}
