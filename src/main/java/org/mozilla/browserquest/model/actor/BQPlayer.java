package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.knownlist.KnownList;
import org.mozilla.browserquest.model.knownlist.PlayerKnownList;
import org.mozilla.browserquest.network.NetworkConnection;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype
public abstract class BQPlayer extends BQCharacter {

    private NetworkConnection connection;

    public BQPlayer() {
        setType(BQType.WARRIOR);
    }

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
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getName(), getHeading().getValue(), 21, 60});
    }
}
