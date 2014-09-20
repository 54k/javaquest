package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.actor.behavior.MovableBehavior;
import org.mozilla.browserquest.model.actor.projection.CharacterProjection;
import org.vertx.java.core.json.JsonArray;

@Prototype(MovableBehavior.class)
public abstract class BQCharacter extends BQObject implements CharacterProjection {

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getHeading().getValue()});
    }
}
