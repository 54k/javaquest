package org.mozilla.browserquest.model;

import org.mozilla.browserquest.model.collection.BQObjectContainer;
import org.mozilla.browserquest.model.collection.BQPlayerContainer;
import org.mozilla.browserquest.model.collection.DefaultBQObjectContainer;
import org.mozilla.browserquest.model.collection.DefaultBQPlayerContainer;

import java.util.ArrayList;
import java.util.List;

public class BQWorldMapRegion {

    private BQWorldMapInstance parent;

    private BQPlayerContainer players = new DefaultBQPlayerContainer();
    private BQObjectContainer<BQObject> objects = new DefaultBQObjectContainer<>();

    private List<BQWorldMapRegion> surroundingRegions = new ArrayList<>();
    private List<BQWorldMapZoneType> zones = new ArrayList<>();

    public BQWorldMapRegion(BQWorldMapInstance parent) {
        this.parent = parent;
    }

    public BQWorldMapInstance getParent() {
        return parent;
    }
}
