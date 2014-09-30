package org.mozilla.browserquest.service;

import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.template.WorldMapTemplate;

import java.util.Map;

public interface DataService {

    WorldMapTemplate getWorldMapTemplate();

    Map<String, CreatureTemplate> getCreatureTemplates();
}
