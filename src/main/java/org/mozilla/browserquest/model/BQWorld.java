package org.mozilla.browserquest.model;

import com.google.inject.Inject;
import org.mozilla.browserquest.template.BQWorldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorld {

    private static final Logger LOGGER = LoggerFactory.getLogger(BQWorld.class);

    private static final int REGION_WIDTH = 28;
    private static final int REGION_HEIGHT = 12;

    private final int width;
    private final int height;
    private final int regionOffset;

    private Map<Integer, BQObject> objects = new ConcurrentHashMap<>();

    private Map<Integer, BQWorldRegion> regions = new ConcurrentHashMap<>();

    @Inject
    public BQWorld(BQWorldTemplate worldTemplate) {
        width = worldTemplate.getWidth();
        height = worldTemplate.getHeight();
        regionOffset = height / REGION_HEIGHT + 1;
        initRegions();
    }

    private void initRegions() {
        for (int i = 0; i < width; i += REGION_WIDTH) {
            for (int j = 0; j < height; j += REGION_HEIGHT) {
                int regionId = getRegionId(i, j);
                regions.put(regionId, new BQWorldRegion());
            }
        }

        for (int i = 0; i < width; i += REGION_WIDTH) {
            for (int j = 0; j < height; j += REGION_HEIGHT) {
                addSurroundingRegions(i, j);
            }
        }

        LOGGER.info("Regions initialized.");
    }

    private int getRegionId(int x, int y) {
        return (x + 1) / REGION_WIDTH * regionOffset + (y + 1) / REGION_HEIGHT;
    }

    private void addSurroundingRegions(int x, int y) {
        BQWorldRegion region = regions.get(getRegionId(x, y));
        for (int i = x - REGION_WIDTH; i <= x + REGION_WIDTH; i += REGION_WIDTH) {
            for (int j = y - REGION_HEIGHT; j <= y + REGION_HEIGHT; j += REGION_HEIGHT) {
                if (isValidRegionPosition(i, j)) {
                    BQWorldRegion sr = regions.get(getRegionId(i, j));
                    if (region != sr) {
                        region.addSurroundingRegion(sr);
                    }
                }
            }
        }
    }

    private boolean isValidRegionPosition(int x, int y) {
        return x >= 0 && y >= 0 && x <= width && y <= height;
    }
}
