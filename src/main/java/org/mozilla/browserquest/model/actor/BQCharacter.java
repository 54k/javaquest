package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.combat.CombatControllerBehavior;
import org.mozilla.browserquest.model.position.MovementControllerBehavior;
import org.mozilla.browserquest.model.projection.CharacterProjection;
import org.mozilla.browserquest.model.status.StatusControllerBehavior;

@ActorPrototype({MovementControllerBehavior.class, CombatControllerBehavior.class, StatusControllerBehavior.class})
public abstract class BQCharacter extends BQObject implements CharacterProjection {

    private int weapon;
    private int armor;

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }
}
