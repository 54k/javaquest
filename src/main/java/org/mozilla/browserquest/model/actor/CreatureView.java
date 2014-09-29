package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorView;
import org.mozilla.browserquest.model.creature.DropController;

@ActorView
public interface CreatureView {

    DropController getDropController();
}
