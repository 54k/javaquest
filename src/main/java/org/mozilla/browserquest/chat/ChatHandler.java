package org.mozilla.browserquest.chat;

import org.mozilla.browserquest.model.actor.BQPlayer;

public interface ChatHandler {

    void handle(BQPlayer player, String message);
}
