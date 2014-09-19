package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.behavior.PositionableBehavior;
import org.mozilla.browserquest.model.actor.knownlist.KnownList;
import org.mozilla.browserquest.model.actor.knownlist.ObjectKnownList;
import org.mozilla.browserquest.model.projection.ObjectProjection;
import org.vertx.java.core.json.JsonArray;

@Prototype({PositionableBehavior.class})
public abstract class BQObject extends Actor implements ObjectProjection {

    private int id;
    private String name;

    private KnownList knownList;

    private BQWorld world;
    private BQWorldRegion region;

    private int x;
    private int y;

    protected BQObject() {
        knownList = initKnownList();
    }

    protected KnownList initKnownList() {
        return new ObjectKnownList(this);
    }

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

    public BQWorld getWorld() {
        return world;
    }

    public void setWorld(BQWorld world) {
        this.world = world;
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

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void setPosition(Position position) {
        setXY(position.getX(), position.getY());
    }

    public abstract JsonArray getInfo();

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
        return getClass().getSimpleName() + "(id=" + id + ", name=" + name + ')';
    }
}
