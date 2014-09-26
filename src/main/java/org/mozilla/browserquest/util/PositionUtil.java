package org.mozilla.browserquest.util;

import org.mozilla.browserquest.model.Area;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;

import java.util.Random;

public final class PositionUtil {

    private PositionUtil() {
    }

    public static boolean isInRange(BQObject o1, BQObject o2, int range) {
        int dy = Math.abs(o1.getY() - o2.getY());
        int dx = Math.abs(o1.getX() - o2.getX());
        return dx <= range && dy <= range;
    }

    public static boolean isOutOfRange(BQObject o1, BQObject o2, int range) {
        return !isInRange(o1, o2, range);
    }

    public static Position getRandomPositionInside(int x, int y, int width, int height) {
        Random random = new Random();
        return new Position(x + random.nextInt(width + 1), y + random.nextInt(height + 1));
    }

    public static Position getRandomPositionInside(Area area) {
        return getRandomPositionInside(area.getX(), area.getY(), area.getWidth(), area.getHeight());
    }

    public static Orientation getRandomHeading() {
        Random random = new Random();
        Orientation[] values = Orientation.values();
        return values[random.nextInt(values.length)];
    }

    public static Position getRandomPositionNear(BQObject object) {
        Random random = new Random();
        Position position = new Position();
        Position objectPosition = object.getPosition();
        position.setXY(objectPosition.getX() + (random.nextInt(2) - 1), objectPosition.getY() + (random.nextInt(2) - 1));
        return position;
    }
}
