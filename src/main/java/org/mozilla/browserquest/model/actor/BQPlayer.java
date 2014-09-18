package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.BQObject;
import org.mozilla.browserquest.network.NetworkConnection;

@Prototype
public abstract class BQPlayer extends BQObject {

    private NetworkConnection connection;

    public NetworkConnection getConnection() {
        return connection;
    }

    public void setConnection(NetworkConnection connection) {
        this.connection = connection;
    }
}
