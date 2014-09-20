package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Heading;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.actor.behavior.PositionableBehavior;
import org.mozilla.browserquest.model.actor.knownlist.KnownList;
import org.mozilla.browserquest.model.actor.knownlist.ObjectKnownList;
import org.mozilla.browserquest.model.actor.projection.ObjectProjection;
import org.vertx.java.core.json.JsonArray;

@Prototype(PositionableBehavior.class)
public abstract class BQObject extends Actor implements ObjectProjection {

    private int id;
    private String name;

    private BQType type;

    private KnownList knownList;

    private BQWorld world;
    private BQWorldRegion region;

    private Position position;
    private Heading heading;

    protected BQObject() {
        position = new Position();
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

    public BQType getType() {
        return type;
    }

    public void setType(BQType type) {
        this.type = type;
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
        return position.getX();
    }

    public void setX(int x) {
        position.setX(x);
    }

    public int getY() {
        return position.getY();
    }

    public void setY(int y) {
        position.setY(y);
    }

    public void setXY(int x, int y) {
        position.setXY(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        setXY(position.getX(), position.getY());
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
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
        return getClass().getSimpleName() + "(id=" + id + ", name=" + name + ')';
    }
}
