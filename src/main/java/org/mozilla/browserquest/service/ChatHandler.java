package org.mozilla.browserquest.service;

import org.mozilla.browserquest.model.actor.BQPlayer;

public interface ChatHandler {

    void handle(BQPlayer player, String message);
}
