package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.Actor.Prototype;
import org.mozilla.browserquest.model.actor.knownlist.KnownList;
import org.mozilla.browserquest.model.actor.knownlist.PlayerKnownList;
import org.mozilla.browserquest.network.NetworkConnection;
import org.vertx.java.core.json.JsonArray;

@Prototype
public abstract class BQPlayer extends BQCharacter {

    private NetworkConnection connection;

    private int hitPoints;
    private int maxHitPoints;

    public BQPlayer() {
        setType(BQType.WARRIOR);
    }

    public NetworkConnection getConnection() {
        return connection;
    }

    public void setConnection(NetworkConnection connection) {
        this.connection = connection;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    @Override
    protected KnownList initKnownList() {
        return new PlayerKnownList(this);
    }

    @Override
    public JsonArray getInfo() {
        return new JsonArray(new Object[]{getId(), getType().getId(), getX(), getY(), getName(), getHeading(), 21, 60});
    }
}
