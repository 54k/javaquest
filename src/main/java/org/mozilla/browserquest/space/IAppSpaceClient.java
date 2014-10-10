package org.mozilla.browserquest.space;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.net.NetworkClient;

public interface IAppSpaceClient {

    void setPawn(PlayerObject pawn);

    PlayerObject getPawn();

    NetworkClient getNetworkClient();

    void register(IAppSpace appSpace);

    void unregister();
}
