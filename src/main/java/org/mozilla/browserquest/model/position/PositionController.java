package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;

public interface PositionController {

    Position getPosition();

    void setPosition(Position position);

    void setPosition(int x, int y);

    BQWorld getWorld();

    void setWorld(BQWorld world);

    BQWorldRegion getRegion();

    boolean isSpawned();

    void updatePosition(int x, int y);

    void updatePosition(Position position);

    void spawn();

    void decay();
}
