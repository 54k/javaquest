package org.mozilla.browserquest.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatureTemplate {

    @JsonProperty("hp")
    private int hitPoints;
    private int weapon;
    private int armor;

    public int getHitPoints() {
        return hitPoints;
    }

    public int getWeapon() {
        return weapon;
    }

    public int getArmor() {
        return armor;
    }
}
