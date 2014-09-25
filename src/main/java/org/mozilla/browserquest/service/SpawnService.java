package org.mozilla.browserquest.service;

import org.mozilla.browserquest.model.BQSpawn;

import java.util.Map;

public interface SpawnService {

    Map<Integer, BQSpawn> getManagedSpawns();
}
