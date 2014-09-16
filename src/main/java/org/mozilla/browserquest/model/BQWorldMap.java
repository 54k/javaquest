package org.mozilla.browserquest.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldMap {

    private Map<Integer, BQWorldMapInstance> worldMapInstancesById = new ConcurrentHashMap<>();
}
