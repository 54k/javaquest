package org.mozilla.browserquest.chat;

import com.google.inject.Inject;
import org.mozilla.browserquest.model.actor.BQPlayer;
import org.mozilla.browserquest.script.ScriptService;

import java.io.File;

public class DefaultChatHandler implements ChatHandler {

    private ChatHandler scriptChatHandler;

    @Inject
    public DefaultChatHandler(ScriptService scriptService) {
        load(scriptService);
    }

    private void load(ScriptService scriptService) {
        scriptChatHandler = scriptService.newProxy(ChatHandler.class, new File(ScriptService.SCRIPT_FOLDER, "chathandler.js"));
    }

    @Override
    public void handle(BQPlayer player, String message) {
        scriptChatHandler.handle(player, message);
    }
}
