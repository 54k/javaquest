package org.mozilla.browserquest.gameserver.model.position;

import org.mozilla.browserquest.gameserver.model.BQWorld;
import org.mozilla.browserquest.gameserver.model.BQWorldRegion;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;

public interface PositionController {

    Position getPosition();

    void setPosition(Position position);

    void setPosition(int x, int y);

    Orientation getOrientation();

    void setOrientation(Orientation orientation);

    BQWorld getWorld();

    void setWorld(BQWorld world);

    BQWorldRegion getRegion();

    boolean isSpawned();

    void updatePosition(int x, int y);

    void updatePosition(Position position);

    void spawn();

    void decay();
}
