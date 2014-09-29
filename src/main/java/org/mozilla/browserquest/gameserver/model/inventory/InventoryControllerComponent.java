package org.mozilla.browserquest.gameserver.model.inventory;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BQCharacter;

@ComponentPrototype(InventoryController.class)
public class InventoryControllerComponent extends Component<BQCharacter> implements InventoryController {

    private int weapon;
    private int armor;

    @Override
    public int getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public void setArmor(int armor) {
        this.armor = armor;
    }
}
