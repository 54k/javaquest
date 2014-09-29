package org.mozilla.browserquest.service;

import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;

public interface ChatHandler {

    void handle(PlayerObject player, String message);
}
