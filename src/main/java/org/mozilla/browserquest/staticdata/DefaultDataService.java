package org.mozilla.browserquest.staticdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mozilla.browserquest.template.BQWorldTemplate;

import java.io.File;

public class DefaultDataService implements DataService {

    public static final File DATA_FOLDER = new File("data/static");

    private BQWorldTemplate worldTemplate;

    public DefaultDataService() {
        load();
    }

    private void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            worldTemplate = mapper.readValue(new File(DATA_FOLDER, "world_map.json"), BQWorldTemplate.class);
        } catch (Throwable t) {
            throw new Error(t);
        }
    }

    @Override
    public BQWorldTemplate getWorldTemplate() {
        return worldTemplate;
    }
}
