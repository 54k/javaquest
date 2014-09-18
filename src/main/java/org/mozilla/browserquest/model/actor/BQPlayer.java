package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.actor.behavior.MovableBehavior;
import org.mozilla.browserquest.model.actor.knownlist.KnownList;
import org.mozilla.browserquest.model.actor.knownlist.PlayerKnownList;
import org.mozilla.browserquest.model.interfaces.MovableProjection;
import org.mozilla.browserquest.network.NetworkConnection;
import org.vertx.java.core.json.JsonArray;

@Prototype({MovableBehavior.class})
public abstract class BQPlayer extends BQObject implements MovableProjection {

    private NetworkConnection connection;

    public NetworkConnection getConnection() {
        return connection;
    }

    public void setConnection(NetworkConnection connection) {
        this.connection = connection;
    }

    @Override
    protected KnownList initKnownList() {
        return new PlayerKnownList(this);
    }

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), 1, getX(), getY(), getName(), 2, 21, 60});
    }
}
