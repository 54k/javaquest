package org.mozilla.browserquest.model;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.actor.knownlist.KnownList;
import org.mozilla.browserquest.model.actor.knownlist.ObjectKnownList;
import org.mozilla.browserquest.model.interfaces.Identifiable;
import org.mozilla.browserquest.model.interfaces.Positionable;
import org.vertx.java.core.json.JsonArray;

@Prototype
public abstract class BQObject extends Actor implements Identifiable, Positionable {

    private int id;
    private String name;

    private KnownList knownList;
    private BQWorldRegion region;

    private int x;
    private int y;

    protected BQObject() {
        knownList = initKnownList();
    }

    protected KnownList initKnownList() {
        return new ObjectKnownList(this);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnownList getKnownList() {
        return knownList;
    }

    @Override
    public BQWorldRegion getRegion() {
        return region;
    }

    @Override
    public void setRegion(BQWorldRegion region) {
        this.region = region;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
    }

    public abstract JsonArray getInfo();

    public void onSpawn() {
    }

    public void onDecay() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BQObject obj = (BQObject) o;

        return id == obj.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", name=" + name + ']';
    }
}
