package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.knownlist.KnownListControllerBehavior;
import org.mozilla.browserquest.model.position.PositionController;
import org.mozilla.browserquest.model.position.PositionControllerBehavior;
import org.mozilla.browserquest.model.projection.ObjectProjection;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype({PositionControllerBehavior.class, KnownListControllerBehavior.class})
public abstract class BQObject extends Actor implements ObjectProjection {

    private int id;
    private String name;

    private BQType type;

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

    public JsonArray getInfo() {
        PositionController positionController = getPositionController();
        Position position = positionController.getPosition();
        Orientation orientation = positionController.getOrientation();
        return new JsonArray(new Object[]{getId(), getType().getId(), position.getX(), position.getY(), orientation.getValue()});
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
