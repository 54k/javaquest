package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldMapZoneType {

    private Map<Integer, BQPlayer> playersById = new ConcurrentHashMap<>();

}
