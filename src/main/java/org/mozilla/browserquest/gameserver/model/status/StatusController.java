package org.mozilla.browserquest.gameserver.model.status;

import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;

public interface StatusController {

    int getHitPoints();

    void setHitPoints(int hitPoints);

    boolean isDead();

    void setDead(boolean dead);

    void heal(BQCharacter healer, int amount);

    void damage(BQCharacter attacker, int amount);

    void revive();

    void die(BQCharacter killer);
}
