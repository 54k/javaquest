package org.mozilla.browserquest.gameserver.service;

import org.mozilla.browserquest.gameserver.model.Area;

import java.util.Map;

public interface MapRegionService {

    Map<Integer, Area> getStartingAreas();
}
