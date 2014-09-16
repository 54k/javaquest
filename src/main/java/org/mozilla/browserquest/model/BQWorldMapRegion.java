package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.actor.BQPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldMapRegion {

    private Map<Integer, BQPlayer> playersById = new ConcurrentHashMap<>();
    private Map<Integer, BQObject> objectsById = new ConcurrentHashMap<>();

    private List<BQWorldMapRegion> surroundingRegions = new ArrayList<>();
    private List<BQWorldMapZoneType> zones = new ArrayList<>();

}
