package org.mozilla.browserquest.model;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BQWorldRegion {

    private Set<BQWorldRegion> surroundingRegions = new HashSet<>();

    public void addSurroundingRegion(BQWorldRegion region) {
        Preconditions.checkNotNull(region);
        surroundingRegions.add(region);
    }

    public Set<BQWorldRegion> getSurroundingRegions() {
        return Collections.unmodifiableSet(surroundingRegions);
    }
}
