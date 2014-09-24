package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.model.BQWorld;
import org.mozilla.browserquest.model.BQWorldRegion;
import org.mozilla.browserquest.model.Heading;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.controller.KnownListControllerBehavior;
import org.mozilla.browserquest.model.controller.PositionControllerBehavior;
import org.mozilla.browserquest.model.projection.ObjectProjection;
import org.vertx.java.core.json.JsonArray;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ActorPrototype({PositionControllerBehavior.class, KnownListControllerBehavior.class})
public abstract class BQObject extends Actor implements ObjectProjection {

    private int id;
    private String name;

    private BQType type;

    private BQWorld world;
    private BQWorldRegion region;

    private Position position;
    private Heading heading;

    private Map<Integer, BQObject> knownObjects = new ConcurrentHashMap<>();
    private Map<Integer, BQPlayer> knownPlayers = new ConcurrentHashMap<>();

    protected BQObject() {
        position = new Position();
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

    public Map<Integer, BQObject> getKnownObjects() {
        return knownObjects;
    }

    public Map<Integer, BQPlayer> getKnownPlayers() {
        return knownPlayers;
    }

    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getHeading().getValue()});
    }

    public int getDistanceToForgetObject(BQObject object) {
        return 0;
    }

    public int getDistanceToFindObject(BQObject object) {
        return 0;
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
