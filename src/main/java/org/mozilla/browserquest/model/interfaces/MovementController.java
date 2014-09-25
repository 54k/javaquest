package org.mozilla.browserquest.model.interfaces;

import org.mozilla.browserquest.model.Position;

public interface MovementController {

    void moveTo(int x, int y);

    void moveTo(Position position);
}
