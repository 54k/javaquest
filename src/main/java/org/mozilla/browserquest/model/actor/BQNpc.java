package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.model.BQObject;

public class BQNpc extends BQObject {

    public BQNpc(int id, String kind, int x, int y) {
        super(id, "npc", kind, x, y);
    }
}
