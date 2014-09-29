package org.mozilla.browserquest.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatureTemplate {

    @JsonProperty("hp")
    private int hitPoints;
    private int weapon;
    private int armor;
    private Map<String, Integer> drops;

    public int getHitPoints() {
        return hitPoints;
    }

    public int getWeapon() {
        return weapon;
    }

    public int getArmor() {
        return armor;
    }

    public Map<String, Integer> getDrops() {
        return drops;
    }
}
