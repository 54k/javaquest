package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorld {

    private Map<Integer, BQPlayer> playersById = new ConcurrentHashMap<>();
    private Map<Integer, BQObject> objectsById = new ConcurrentHashMap<>();

    private Map<Integer, BQWorldMap> worldMapById = new ConcurrentHashMap<>();
}
