package org.mozilla.browserquest.model.interfaces;

import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;

public interface Positionable {

    BQWorld getWorld();

    void setWorld(BQWorld world);

    BQWorldRegion getRegion();

    void setRegion(BQWorldRegion region);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    void setXY(int x, int y);

    Position getPosition();

    void setPosition(Position position);
}
