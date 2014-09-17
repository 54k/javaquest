package org.mozilla.browserquest.model;

import java.util.ArrayList;
import java.util.List;

public class BQWorldMap {

    private int id;
    private List<BQWorldMapInstance> worldMapInstances = new ArrayList<>();

    public int getId() {
        return id;
    }

    public BQWorldMapInstance getAvailableWorldMapInstance() {
        return worldMapInstances.get(0);
    }
}
