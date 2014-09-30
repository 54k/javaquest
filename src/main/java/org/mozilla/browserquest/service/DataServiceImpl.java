package org.mozilla.browserquest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.template.WorldMapTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataServiceImpl implements DataService {

    public static final File DATA_FOLDER = new File("data/static");

    private WorldMapTemplate worldMapTemplate;
    private Map<String, CreatureTemplate> creatureTemplates;

    public DataServiceImpl() {
        load();
    }

    private void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            worldMapTemplate = mapper.readValue(new File(DATA_FOLDER, "world_map.json"), WorldMapTemplate.class);
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, CreatureTemplate.class);
            creatureTemplates = mapper.readValue(new File(DATA_FOLDER, "creature_templates.json"), mapType);
        } catch (Throwable t) {
            throw new Error(t);
        }
    }

    @Override
    public WorldMapTemplate getWorldMapTemplate() {
        return worldMapTemplate;
    }

    @Override
    public Map<String, CreatureTemplate> getCreatureTemplates() {
        return creatureTemplates;
    }
}
