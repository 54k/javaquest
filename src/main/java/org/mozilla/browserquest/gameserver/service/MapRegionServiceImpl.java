package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.service.DataService;
import org.mozilla.browserquest.template.CheckpointTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapRegionServiceImpl implements MapRegionService {

    @LazyInject
    private DataService dataService;

    private Map<Integer, Area> startingAreas = new ConcurrentHashMap<>();
    private Map<Integer, Area> spawnAreas = new ConcurrentHashMap<>();

    public MapRegionServiceImpl() {
        loadCheckpoints(dataService.getWorldTemplate().getCheckpoints());
    }

    private void loadCheckpoints(List<CheckpointTemplate> checkpoints) {
        for (CheckpointTemplate checkpoint : checkpoints) {
            Area area = new Area(checkpoint.getX(), checkpoint.getY(), checkpoint.getW(), checkpoint.getH());
            if (checkpoint.getS() == 1) {
                startingAreas.put(checkpoint.getId(), area);
            } else {
                spawnAreas.put(checkpoint.getId(), area);
            }
        }
    }

    @Override
    public Map<Integer, Area> getStartingAreas() {
        return Collections.unmodifiableMap(startingAreas);
    }

    @Override
    public Map<Integer, Area> getSpawnAreas() {
        return Collections.unmodifiableMap(spawnAreas);
    }
}
