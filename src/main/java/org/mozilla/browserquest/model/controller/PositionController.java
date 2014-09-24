package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.model.Position;

public interface PositionController {

    void setX(int x);

    void setY(int y);

    void setXY(int x, int y);

    void setPosition(Position position);

    void spawnMe();

    void decayMe();

    boolean isSpawned();
}
