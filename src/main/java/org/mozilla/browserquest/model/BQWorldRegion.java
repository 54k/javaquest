package org.mozilla.browserquest.model;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorldRegion {

    private Map<Integer, BQObject> objects = new ConcurrentHashMap<>();

    private Set<BQWorldRegion> surroundingRegions = new HashSet<>();

    public void addSurroundingRegion(BQWorldRegion region) {
        Preconditions.checkNotNull(region);
        surroundingRegions.add(region);
    }

    public Set<BQWorldRegion> getSurroundingRegions() {
        return Collections.unmodifiableSet(surroundingRegions);
    }

    public Map<Integer, BQObject> getObjects() {
        return Collections.unmodifiableMap(objects);
    }
}
