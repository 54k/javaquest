package org.mozilla.browserquest.model;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.template.BQWorldTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BQWorld {

    private static final int REGION_WIDTH = 28;
    private static final int REGION_HEIGHT = 12;

    private final int width;
    private final int height;
    private final int regionOffset;

    private Map<Integer, BQObject> objects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> players = new ConcurrentHashMap<>();

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
                    region.addSurroundingRegion(sr);
                }
            }
        }
    }

    private boolean isValidRegionPosition(int x, int y) {
        return x >= 0 && y >= 0 && x <= width && y <= height;
    }

    public BQWorldRegion getRegion(Position position) {
        return getRegion(position.getX(), position.getY());
    }

    public BQWorldRegion getRegion(int x, int y) {
        return regions.get(getRegionId(x, y));
    }

    public void addObject(BQObject object) {
        objects.put(object.getId(), object);
        if (object instanceof BQPlayer) {
            addPlayer((BQPlayer) object);
        }
    }

    public void removeObject(BQObject object) {
        objects.remove(object.getId(), object);
        if (object instanceof BQPlayer) {
            removePlayer((BQPlayer) object);
        }
    }

    public BQObject findObject(int id) {
        return objects.get(id);
    }

    public Map<Integer, BQObject> getObjects() {
        return Collections.unmodifiableMap(players);
    }

    private void addPlayer(BQPlayer player) {
        players.put(player.getId(), player);
    }

    private void removePlayer(BQPlayer player) {
        players.remove(player.getId());
    }

    public Map<Integer, BQPlayer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    public void spawnObject(BQObject object) {
        BQWorldRegion region = getRegion(object.getPosition());
        object.setRegion(region);
        region.addObject(object);
        object.getKnownList().updateKnownObjects();
        object.onSpawn();
    }

    public void decayObject(BQObject object) {
        BQWorldRegion region = object.getRegion();
        region.removeObject(object);
        object.setRegion(null);
        object.getKnownList().clearKnownObjects();
        object.onDecay();
    }

    public void updateObject(BQObject object) {
        BQWorldRegion oldRegion = object.getRegion();
        BQWorldRegion newRegion = getRegion(object.getPosition());

        if (oldRegion != newRegion) {
            oldRegion.removeObject(object);
            object.setRegion(newRegion);
            newRegion.addObject(object);
        }

        object.getKnownList().updateKnownObjects();
    }
}
