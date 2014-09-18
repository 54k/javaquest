package org.mozilla.browserquest.model.behavior;

import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.knownlist.KnownList;

public interface Positionable {

    KnownList<BQObject> getKnownList();

    BQWorldRegion getRegion();

    void setRegion(BQWorldRegion region);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    Position getPosition();

    void setPosition(Position position);
}
