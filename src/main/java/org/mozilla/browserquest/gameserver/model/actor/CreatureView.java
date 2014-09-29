package org.mozilla.browserquest.gameserver.model.actor;

import org.mozilla.browserquest.actor.ActorView;
import org.mozilla.browserquest.gameserver.model.creature.DropController;

@ActorView
public interface CreatureView {

    DropController getDropController();
}
