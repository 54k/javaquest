package org.mozilla.browserquest.util;

import org.mozilla.browserquest.model.actor.BQObject;

public final class PositionUtil {

    private PositionUtil() {
    }

    public static boolean inRange(BQObject o1, BQObject o2, int range) {
        int dy = Math.abs(o1.getY() - o2.getY());
        int dx = Math.abs(o1.getX() - o2.getX());
        return dx <= range && dy <= range;
    }

    public static boolean outOfRange(BQObject o1, BQObject o2, int range) {
        return !inRange(o1, o2, range);
    }
}
