package org.mozilla.browserquest.model.position;

import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.BQObject;

public interface MovementController {

    void moveTo(int x, int y);

    void moveTo(Position position);

    void moveTo(BQObject object);
}
