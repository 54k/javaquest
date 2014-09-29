package org.mozilla.browserquest.util;

import org.mozilla.browserquest.gameserver.model.Area;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;

import java.util.Random;

public final class PositionUtil {

    private PositionUtil() {
    }

    public static boolean isInRange(BQObject o1, BQObject o2, int range) {
        Position pos1 = o1.getPositionController().getPosition();
        Position pos2 = o2.getPositionController().getPosition();
        int dx = Math.abs(pos1.getX() - pos2.getX());
        int dy = Math.abs(pos1.getY() - pos2.getY());
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
        Position objectPosition = object.getPositionController().getPosition();
        position.setXY(objectPosition.getX() + (random.nextInt(2) - 1), objectPosition.getY() + (random.nextInt(2) - 1));
        return position;
    }
}
