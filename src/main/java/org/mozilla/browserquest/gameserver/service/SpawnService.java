package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.WorldMapInstance;

public interface SpawnService {

    void spawnCreatures(WorldMapInstance worldMapInstance);

    void spawnStaticObjects(WorldMapInstance worldMapInstance);
}
