package org.mozilla.browserquest.model.behavior;

import org.mozilla.browserquest.actor.Behavior;
import org.mozilla.browserquest.actor.Behavior.Prototype;
import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.knownlist.KnownList;
import org.mozilla.browserquest.model.knownlist.ObjectKnownList;

@Prototype(Positionable.class)
public class PositionableBehavior extends Behavior<BQObject> implements Positionable {

    private KnownList<BQObject> knownList;
    private BQWorldRegion region;

    private int x;
    private int y;

    public PositionableBehavior() {
        knownList = newKnownList();
    }

    protected KnownList<BQObject> newKnownList() {
        return new ObjectKnownList<>(getActor());
    }

    public KnownList<BQObject> getKnownList() {
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
}
