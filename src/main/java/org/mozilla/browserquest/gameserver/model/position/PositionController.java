package org.mozilla.browserquest.gameserver.model.position;

import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.World;
import org.mozilla.browserquest.gameserver.model.WorldRegion;

public interface PositionController {

    Position getPosition();

    void setPosition(Position position);

    void setPosition(int x, int y);

    Orientation getOrientation();

    void setOrientation(Orientation orientation);

    World getWorld();

    void setWorld(World world);

    WorldRegion getRegion();

    boolean isSpawned();

    void updatePosition(int x, int y);

    void updatePosition(Position position);

    void spawn();

    void decay();
}
