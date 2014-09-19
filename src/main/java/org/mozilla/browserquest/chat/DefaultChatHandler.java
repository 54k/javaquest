package org.mozilla.browserquest.chat;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.script.ScriptService;

import java.io.File;

public class DefaultChatHandler implements ChatHandler {

    @Inject
    private ScriptService scriptService;

    @Override
    public void handle(BQPlayer player, String message) {
        scriptService.newProxy(ChatHandler.class, new File(ScriptService.SCRIPT_FOLDER, "chathandler.js")).handle(player, message);
    }
}
