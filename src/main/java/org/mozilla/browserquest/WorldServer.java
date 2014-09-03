package org.mozilla.browserquest;

import java.util.HashSet;
import java.util.Set;

public class WorldServer {

    private String name;
    private int maxPlayers;
    private int playersCount;

    private NetServer server;

    private Set<Player> players = new HashSet<>();

    public WorldServer(String name, int maxPlayers, NetServer server) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.server = server;
    }

    public void addPlayer(Player player) {
        players.add(player);
        playersCount++;
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
