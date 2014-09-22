package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.model.Position;

public interface MovementController {

    void moveTo(int x, int y);

    void moveTo(Position position);
}
