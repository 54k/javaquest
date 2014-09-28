package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.BQType;
import org.mozilla.browserquest.model.Orientation;
import org.mozilla.browserquest.model.Position;
import org.mozilla.browserquest.model.player.PlayerControllerComponent;
import org.mozilla.browserquest.model.player.PlayerStatsCalculatorComponent;
import org.mozilla.browserquest.model.position.PositionController;
import org.mozilla.browserquest.network.NetworkConnection;
import org.vertx.java.core.json.JsonArray;

@ActorPrototype({PlayerControllerComponent.class, PlayerStatsCalculatorComponent.class})
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
    public JsonArray getInfo() {
        PositionController positionController = getPositionController();
        Position position = positionController.getPosition();
        Orientation orientation = positionController.getOrientation();
        return new JsonArray(new Object[]{getId(), getType().getId(), position.getX(), position.getY(), getName(), orientation.getValue(), 21, 60});
    }
}
