package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.gameserver.model.actor.BQPlayer;

import java.io.File;

public class DefaultChatHandler implements ChatHandler {

    @Inject
    private ScriptService scriptService;

    @Override
    public void handle(BQPlayer player, String message) {
        scriptService.newProxy(ChatHandler.class, new File(ScriptService.SCRIPT_FOLDER, "chathandler.js")).handle(player, message);
    }
}
