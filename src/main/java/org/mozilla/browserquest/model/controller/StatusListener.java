package org.mozilla.browserquest.model.controller;

import org.mozilla.browserquest.model.actor.BQCharacter;
import org.mozilla.browserquest.util.Listener;

@Listener
public interface StatusListener {

    void onRevive();

    void onDie(BQCharacter killer);
}
