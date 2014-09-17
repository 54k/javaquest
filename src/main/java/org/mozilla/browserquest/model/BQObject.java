package org.mozilla.browserquest.model;

import org.mozilla.browserquest.Position;
import org.mozilla.browserquest.model.knownlist.KnownList;
import org.mozilla.browserquest.model.knownlist.ObjectKnownList;

public abstract class BQObject {

    private int objectId;
    private KnownList knownList;

    private BQWorldRegion region;

    private int x;
    private int y;

    public BQObject(int objectId) {
        this.objectId = objectId;
        knownList = newKnownList();
    }

    protected KnownList newKnownList() {
        return new ObjectKnownList(this);
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public KnownList getKnownList() {
        return knownList;
    }

    public BQWorldRegion getRegion() {
        return region;
    }

    public void setRegion(BQWorldRegion region) {
        this.region = region;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
    }


    public void onObjectAddedToKnownList(BQObject object) {
    }

    public void onObjectRemovedFromKnownList(BQObject object) {
    }
}
