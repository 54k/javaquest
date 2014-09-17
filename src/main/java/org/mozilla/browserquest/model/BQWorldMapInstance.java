package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.collection.BQObjectContainer;
import org.mozilla.browserquest.model.collection.BQPlayerContainer;
import org.mozilla.browserquest.model.collection.DefaultBQObjectContainer;
import org.mozilla.browserquest.model.collection.DefaultBQPlayerContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldMapInstance {

    private BQPlayerContainer players = new DefaultBQPlayerContainer();
    private BQObjectContainer<BQObject> objects = new DefaultBQObjectContainer<>();

    private Map<Integer, BQWorldMapRegion> worldMapRegionsById = new ConcurrentHashMap<>();
}
