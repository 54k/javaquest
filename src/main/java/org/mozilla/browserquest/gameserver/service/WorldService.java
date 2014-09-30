package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.WorldMapInstance;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

import java.util.Map;

public interface WorldService {

    void addObject(BaseObject object);

    void removeObject(BaseObject object);

    void broadcastPopulation();

    BaseObject findObject(int id);

    Map<Integer, BaseObject> getObjects();

    Map<Integer, PlayerObject> getPlayers();

    WorldMapInstance createWorldMapInstance(int maxPlayers);

    Map<Integer, WorldMapInstance> getWorldMapInstances();

    WorldMapInstance getAvailableWorldMapInstance();
}
