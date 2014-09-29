package org.mozilla.browserquest.gameserver.model.position;

import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;

public interface MovementController {

    void moveTo(int x, int y);

    void moveTo(Position position);

    void moveTo(BQObject object);

    void teleportTo(int x, int y);

    void teleportTo(Position position);

    void teleportTo(BQObject object);
}
