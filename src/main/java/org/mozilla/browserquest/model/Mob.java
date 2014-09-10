package org.mozilla.browserquest.model;

import java.util.HashSet;
import java.util.Set;

public class Mob extends Character {

    private Set<Entity> hateSet = new HashSet<>();

    public Mob(int id, String kind, int x, int y) {
        super(id, "mob", kind, x, y);
    }
}
