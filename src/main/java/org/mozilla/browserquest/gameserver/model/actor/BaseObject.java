package org.mozilla.browserquest.gameserver.model.actor;

import com.google.common.base.Preconditions;
import org.mozilla.browserquest.actor.Actor;
import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.Position;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListControllerComponent;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.gameserver.model.position.PositionControllerComponent;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype({PositionControllerComponent.class, KnownListControllerComponent.class})
public abstract class BaseObject extends Actor implements BaseObjectView {

    private int id;
    private String name;
    private InstanceType instanceType;

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

    public InstanceType getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(InstanceType instanceType) {
        Preconditions.checkArgument(instanceType.getPrototype().isAssignableFrom(getClass()));
        this.instanceType = instanceType;
    }

    public JsonArray getInfo() {
        PositionController positionController = getPositionController();
        Position position = positionController.getPosition();
        Orientation orientation = positionController.getOrientation();
        return new JsonArray(new Object[]{getId(), getInstanceType().getId(), position.getX(), position.getY(), orientation.getValue()});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseObject obj = (BaseObject) o;

        return id == obj.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id + ", name=" + name + ", instanceType=" + instanceType.name() + ')';
    }
}
