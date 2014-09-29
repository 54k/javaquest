package org.mozilla.browserquest.gameserver.model.status;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;

public interface StatusController {

    int getHitPoints();

    void setHitPoints(int hitPoints);

    boolean isDead();

    void setDead(boolean dead);

    void heal(CharacterObject healer, int amount);

    void damage(CharacterObject attacker, int amount);

    void revive();

    void die(CharacterObject killer);
}
