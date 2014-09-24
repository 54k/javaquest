package org.mozilla.browserquest.service;

import org.mozilla.browserquest.template.CreatureTemplate;
import org.mozilla.browserquest.template.WorldTemplate;

import java.util.Map;

public interface DataService {

    WorldTemplate getWorldTemplate();

    Map<String, CreatureTemplate> getCreatureTemplates();
}
