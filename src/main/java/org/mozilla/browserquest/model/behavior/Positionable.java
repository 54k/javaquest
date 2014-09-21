package org.mozilla.browserquest.model.behavior;

import org.mozilla.browserquest.model.Position;

public interface Positionable {

    void setX(int x);

    void setY(int y);

    void setXY(int x, int y);

    void setPosition(Position position);

    void spawnMe();

    void decayMe();
}
