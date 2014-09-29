package org.mozilla.browserquest.gameserver.model.combat;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;

import java.util.Map;

public interface CombatController {

    CharacterObject getTarget();

    void setTarget(CharacterObject target);

    Map<Integer, CharacterObject> getAttackers();

    void addAttacker(CharacterObject attacker);

    void removeAttacker(CharacterObject attacker);

    void clearAttackers();

    void attack(CharacterObject target);
}
